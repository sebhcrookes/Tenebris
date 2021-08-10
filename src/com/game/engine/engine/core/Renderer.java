package com.game.engine.engine.core;

import com.game.engine.engine.gfx.Colour;
import com.game.engine.engine.gfx.Font;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.gfx.temp.ImageData;
import com.game.engine.engine.gfx.temp.LightData;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Renderer {

    public Font font = Font.STANDARD;
    private final GameEngine engine;

    private final ArrayList<ImageData> tempImageData = new ArrayList<>();
    private final ArrayList<LightData> tempLightData = new ArrayList<>();

    private final int screenWidth;
    private final int screenHeight;

    private final int[] pixels;
    private final int[] zBuffer;
    private int[] lightMap;
    private int[] lightBlock;

    private float camX, camY;
    private int zDepth = 0;

    private final int ambientColour = 0xFF555555;

    private boolean processing = false;

    public Renderer(GameEngine engine) {
        this.engine = engine;

        screenWidth = this.engine.getSettings().getWidth();
        screenHeight = this.engine.getSettings().getHeight();
        pixels = ((DataBufferInt) this.engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[pixels.length];

        if (this.engine.getSettings().isLightingEnabled()) { // Initialise the lightmap arrays
            lightMap = new int[pixels.length];
            lightBlock = new int[pixels.length];
        }
    }


    //================================================================================
    // Direct pixel-modification functions
    //================================================================================

    public void clear() {
        Arrays.fill(pixels, engine.getClearColour());
        Arrays.fill(zBuffer, 0);
        if (engine.getSettings().isLightingEnabled()) {
            Arrays.fill(lightMap, ambientColour);
            Arrays.fill(lightBlock, Light.NONE);
        }
    }

    public void process() {
        processing = true;

        tempImageData.sort(new Comparator<ImageData>() { // Sort our image data from back to front
            @Override
            public int compare(ImageData i0, ImageData i1) {
                if (i0.zDepth < i1.zDepth) {
                    return -1;
                }
                if (i0.zDepth < i1.zDepth) {
                    return 1;
                }
                return 0;
            }
        });

        for (ImageData iR : tempImageData) {
            setzDepth(iR.zDepth); // Set the zDepth to the value of the next image
            drawImage(iR.image, iR.offX, iR.offY); // Draw the image
        }

        if (engine.getSettings().isLightingEnabled()) { // Draw and blend lighting together
            for (LightData l : tempLightData) {
                drawLightRequest(l.light, l.posX, l.posY);
            }

            for (int i = 0; i < pixels.length; i++) {
                float red = Colour.getRed(lightMap[i]); // Get the red value of our pixel
                float green = Colour.getBlue(lightMap[i]); // Get the green value of our pixel
                float blue = Colour.getGreen(lightMap[i]); // Get the blue value of our pixel

                pixels[i] = ((int) (((pixels[i] >> 16) & 0xFF) * red) << 16 | ((int) (((pixels[i] >> 8) & 0xFF) * green) << 8 | ((int) ((pixels[i] & 0xFF) * blue))));
            }
        }

        tempImageData.clear();
        tempLightData.clear();
        processing = false;
    }

    public void setPixel(int x, int y, int value) {
        int alpha = (int) Colour.getAlpha(value); // Get the alpha value from the colour

        if ((x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) || alpha == 0)
            return; // Don't render pixels off-screen or with an alpha of 0

        int pixelIndex = x + y * screenWidth;

        if (zBuffer[pixelIndex] > zDepth)
            return;

        zBuffer[pixelIndex] = zDepth;

        if (alpha == 255) // Render a solid colour (with alpha 255)
            pixels[pixelIndex] = value;
        else { // If our pixel has alpha, we want to blend the colours together
            int pixelColour = pixels[pixelIndex];
            int newRed = ((pixelColour >> 16) & 0xff) - (int) ((((pixelColour >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int newGreen = ((pixelColour >> 8) & 0xff) - (int) ((((pixelColour >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int newBlue = (pixelColour & 0xff) - (int) (((pixelColour & 0xff) - (value & 0xff)) * (alpha / 255f));

            pixels[pixelIndex] = Colour.getColour(newRed, newGreen, newBlue); // Finally, set the value of the pixel
        }
    }


    //================================================================================
    // Shape/image/text drawing functions
    //================================================================================

    public void drawText(String text, int posX, int posY, int colour) {

        posX -= camX;
        posY -= camY;

        // Reserved colours
        int bgColour = 0xFFFF00FF;
        int charStart = 0xFF0000FF;
        int charEnd = 0xFFFFFF00;

        int offset = 0; // The offset we use while drawing

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i); // Get the unicode value for the character we are on

            for (int y = 0; y < font.getFontImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    int pixelColour = font.getFontImage().getPixels()[x + font.getOffsets()[unicode] + y * font.getFontImage().getWidth()];
                    if (pixelColour != bgColour && pixelColour != charStart && pixelColour != charEnd) {
                        setPixel(x + posX + offset, y + posY, colour); // Set pixels on screen to the un-reserved font pixels
                    }
                }
            }
            offset += font.getWidths()[unicode]; // Add the width of the previously drawn character to our offset
        }
    }

    public void drawImage(Image image, int posX, int posY) {

        posX -= camX;
        posY -= camY;

        posX -= image.getRotOffX();
        posY -= image.getRotOffY();

        if (image.isAlpha() && !processing) // Process our alpha images and layer them correctly
            tempImageData.add(new ImageData(image, zDepth, posX, posY));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                setPixel(x + posX, y + posY, image.getPixels()[(x) + (y) * image.getWidth()]);
                if (engine.getSettings().isLightingEnabled())
                    setLightBlock(x + posX, y + posY, image.getLightBlock()); // Set the pixel to a light-block if it blocks light
            }
        }
    }

    public void drawRect(int posX, int posY, int width, int height, int colour) {

        posX -= camX;
        posY -= camY;

        for (int y = 0; y <= height; y++) { // Drawing the vertical sides
            setPixel(posX, y + posY, colour);
            setPixel(posX + width, y + posY, colour);
        }

        for (int x = 0; x <= width; x++) { // Drawing the horizontal sides
            setPixel(posX + x, posY, colour);
            setPixel(posX + x, posY + height, colour);
        }
    }

    public void drawFillRect(float posX, float posY, int width, int height, int colour) {

        posX -= camX;
        posY -= camY;

        for (int y = 0; y <= height; y++) { // Loops through every pixel in the rect and sets the pixel
            for (int x = 0; x <= width; x++) {
                setPixel((int) (x + posX), (int) (y + posY), colour);
            }
        }
    }

    public void drawLine(int x0, int y0, int x1, int y1, int colour) {
        int dx = Math.abs(x1 - x0); // Change in x
        int dy = Math.abs(y1 - y0); // Change in y

        // Determining which direction we're heading in
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) { // Using Bresenham's line drawing algorithm

            setPixel(x0, y0, colour);

            if (x0 == x1 && y0 == y1) break; // We reached the destination

            e2 = 2 * err;
            if (e2 > -1 * dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    public void drawCircle(int offX, int offY, int radius, int colour) {
        offX = offX + (radius);
        offY = offY + (radius);
        int d = (5 - radius * 4) / 4;
        int x = 0;
        int y = radius;

        while (x <= y) {
            setPixel(offX + x, offY + y, colour);
            setPixel(offX + x, offY - y, colour);
            setPixel(offX - x, offY + y, colour);
            setPixel(offX - x, offY - y, colour);
            setPixel(offX + y, offY + x, colour);
            setPixel(offX + y, offY - x, colour);
            setPixel(offX - y, offY + x, colour);
            setPixel(offX - y, offY - x, colour);
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        }
    }


    //================================================================================
    // Light-related functions
    //================================================================================

    public void drawLight(Light light, int posX, int posY) {
        tempLightData.add(new LightData(light, posX, posY));
    }

    public void setLightMap(int x, int y, int value) {

        if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight)
            return; // Don't render

        int baseColour = lightMap[x + y * screenWidth];

        int maxRed = Math.max((baseColour >> 16) & 0xff, (value >> 16) & 0xff);
        int maxGreen = Math.max((baseColour >> 8) & 0xff, (value >> 8) & 0xff);
        int maxBlue = Math.max(baseColour & 0xff, value & 0xff);

        lightMap[x + y * screenWidth] = (maxRed << 16 | maxGreen << 8 | maxBlue);
    }

    public void setLightBlock(int x, int y, int value) {
        if (x < 0 || x >= screenWidth || y < 0 || y >= screenHeight)
            return; // Don't render

        if (zBuffer[x + y * screenWidth] > zDepth)
            return;

        lightBlock[x + y * screenWidth] = value;
    }

    private void drawLightRequest(Light l, int offX, int offY) {

        offX -= camX;
        offY -= camY;

        for (int i = 0; i <= l.getDiameter(); i++) {
            drawLightLine(l, l.getRadius(), l.getRadius(), i, 0, offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), i, l.getDiameter(), offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), 0, i, offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), l.getDiameter(), i, offX, offY);
        }
    }

    private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {
        int dx = Math.abs(x1 - x0); // Change in x
        int dy = Math.abs(y1 - y0); // Change in y

        // Determining which direction we're heading in
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while (true) { // Using Bresenham's line drawing algorithm

            int screenX = x0 - l.getRadius() + offX;
            int screenY = y0 - l.getRadius() + offY;

            if (screenX < 0 || screenX >= screenWidth || screenY < 0 || screenY >= screenHeight)
                return;

            int lightColour = l.getLightValue(x0, y0);
            if (lightColour == 0)
                return;

            if (lightBlock[screenX + screenY * screenWidth] == Light.FULL)
                return;

            setLightMap(screenX, screenY, lightColour);

            if (x0 == x1 && y0 == y1) break; // We reached the destination

            e2 = 2 * err;
            if (e2 > -1 * dy) {
                err -= dy;
                x0 += sx;
            }

            if (e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    //================================================================================
    // Getters and Setters
    //================================================================================

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getzDepth() {
        return zDepth;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }
}