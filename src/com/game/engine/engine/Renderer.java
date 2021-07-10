package com.game.engine.engine;

import com.game.engine.engine.gfx.*;
import com.game.engine.engine.position.Vector2;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Renderer {

    private GameEngine engine;

    private ArrayList<ImageRequest> imageRequest = new ArrayList<>();
    private ArrayList<LightRequest> lightRequest = new ArrayList<>();

    private int pW, pH;
    private int[] p;
    private int[] zb;
    private int[] lm;
    private int[] lb;

    private float camX, camY;
    private int zDepth = 0;

    private int ambientColour = 0xFF555555;

    public Font font = Font.STANDARD;
    private boolean processing = false;

    public Renderer(GameEngine engine) {

        this.engine = engine;

        pW = this.engine.getWidth();
        pH = this.engine.getHeight();
        p = ((DataBufferInt) this.engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zb = new int[p.length];
        if(this.engine.getSettings().isLightingEnabled()) {
            lm = new int[p.length];
            lb = new int[p.length];
        }
    }

    public void clear() {
        Arrays.fill(p, engine.getClearColour());
        Arrays.fill(zb, 0);
        if(engine.getSettings().isLightingEnabled()) {
            Arrays.fill(lm, ambientColour);
            Arrays.fill(lb, Light.NONE);
        }
    }

    public void process() {
        processing = true;

        imageRequest.sort(new Comparator<ImageRequest>() {
            @Override
            public int compare(ImageRequest i0, ImageRequest i1) {
                if (i0.zDepth < i1.zDepth) {
                    return -1;
                }
                if (i0.zDepth < i1.zDepth) {
                    return 1;
                }
                return 0;
            }
        });

        for (ImageRequest iR : imageRequest) {
            setzDepth(iR.zDepth);
            drawImage(iR.image, iR.offX, iR.offY);
        }

        if(engine.getSettings().isLightingEnabled()) {

            // Draw lighting
            for (int i = 0; i < lightRequest.size(); i++) {
                LightRequest l = lightRequest.get(i);
                drawLightRequest(l.light, l.position.X, l.position.Y);
            }

            for (int i = 0; i < p.length; i++) {
                float r = ((lm[i] >> 16) & 0xFF) / 255f;
                float g = ((lm[i] >> 8) & 0xFF) / 255f;
                float b = (lm[i] & 0xFF) / 255f;

                p[i] = ((int) (((p[i] >> 16) & 0xFF) * r) << 16 | ((int) (((p[i] >> 8) & 0xFF) * g) << 8 | ((int) ((p[i] & 0xFF) * b))));
            }
        }

        imageRequest.clear();
        lightRequest.clear();
        processing = false;
    }

    /**
     * Sets a specific pixel on the window
     * @param x X-Position
     * @param y Y-Position
     * @param value Colour of pixel
     */
    public void setPixel(int x, int y, int value) {
        int alpha = ((value >> 24) & 0xff);
        x -= camX;
        y -= camY;

        if((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0)
            return; // Don't render

        int index = x + y * pW;

        if(zb[index] > zDepth) {
            return;
        }
        zb[index] = zDepth;

        if(alpha == 255)
            p[index] = value;
        else {
            int pixelColour = p[index];
            int newRed = ((pixelColour >> 16) & 0xff) - (int) ((((pixelColour >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int newGreen = ((pixelColour >> 8) & 0xff) - (int) ((((pixelColour >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int newBlue = (pixelColour & 0xff) - (int) (((pixelColour & 0xff) - (value & 0xff)) * (alpha / 255f));

            p[index] = (newRed << 16 | newGreen << 8 | newBlue);
        }
    }

    public void setLightMap(int x, int y, int value) {
        if(x < 0 || x >= pW || y < 0 || y >= pH)
            return; // Don't render

        int baseColour = lm[x + y * pW];

        int maxRed = Math.max((baseColour >> 16) & 0xff, (value >> 16) & 0xff);
        int maxGreen = Math.max((baseColour >> 8) & 0xff, (value >> 8) & 0xff);
        int maxBlue = Math.max(baseColour & 0xff, value & 0xff);

        lm[x + y * pW] = (maxRed << 16 | maxGreen << 8 | maxBlue);
    }

    public void setLightBlock(int x, int y, int value) {
        if(x < 0 || x >= pW || y < 0 || y >= pH)
            return; // Don't render

        if(zb[x + y * pW] > zDepth) {
            return;
        }

        lb[x + y * pW] = value;
    }


    /**
     * Draws text to the window at a specific location
     *
     * @param text Text to draw
     * @param offX X-Position
     * @param offY Y-Position
     * @param colour Colour of the text
     */
    public void drawText(String text, int offX, int offY, int colour) {
        int offset = 0;

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);

            for(int y = 0; y < font.getFontImage().getHeight(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    int pixelColour = font.getFontImage().getPixels()[x + font.getOffsets()[unicode] + y * font.getFontImage().getWidth()];
                    if(pixelColour != 0xffff00ff && pixelColour != 0xff0000ff && pixelColour != 0xffffff00) {
                        setPixel(x + offX + offset, y + offY, colour);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawText(String text, Vector2 position, int colour) {
        drawText(text, position.X, position.Y, colour);
    }


    /**
     * Draws an image to the window at a specific location
     *
     * @param image Image to draw
     * @param offX X-Position
     * @param offY Y-Position
     */
    public void drawImage(Image image, int offX, int offY) {
        if(image.isAlpha() && !processing) {
            imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
        }

        int newX = 0;
        int newY = 0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x + offX,y + offY, image.getPixels()[(x - newX) + (y - newY) * image.getWidth()]);
                if(engine.getSettings().isLightingEnabled())
                    setLightBlock(x + offX, y + offY, image.getLightBlock());
            }
        }
    }

    /**
     * Draws a rectangle to the window at a specific location
     * @param offX X-Position
     * @param offY Y-Position
     * @param width Width
     * @param height Height
     * @param colour Colour of rectangle
     */
    public void drawRect(int offX, int offY, int width, int height, int colour) {
        for(int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, colour);
            setPixel(offX + width, y + offY, colour);
        }

        for(int x = 0; x <= width; x++) {
            setPixel(offX + x, offY, colour);
            setPixel(offX + x, offY + height, colour);
        }
    }

    public void drawCurvedRect(int offX, int offY, int width, int height, int colour) {
        for(int y = 1; y <= height - 1; y++) {
            setPixel(offX, y + offY, colour);
            setPixel(offX + width, y + offY, colour);
        }

        for(int x = 1; x <= width - 1; x++) {
            setPixel(offX + x, offY, colour);
            setPixel(offX + x, offY + height, colour);
        }
    }

    /**
     * Draws a full rectangle to the window at a specific location
     *
     * @param position position of rect on screen
     * @param width width of rect
     * @param height height of rect
     * @param colour colour of rect
     */
    public void drawFillRect(Vector2 position, int width, int height, int colour) {
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                setPixel(x + position.X, y + position.Y, colour);
            }
        }
    }

    /**
     * Draws a light to the window at a specific location
     *
     * @param light light to draw
     * @param position position of light on screen
     */
    public void drawLight(Light light, Vector2 position) {
        lightRequest.add(new LightRequest(light, position));
    }

    private void drawLightRequest(Light l, int offX, int offY) {
        for(int i = 0; i <= l.getDiameter(); i++) {
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

        while(true) {

            int screenX = x0 - l.getRadius() + offX;
            int screenY = y0 - l.getRadius() + offY;

            if(screenX < 0 || screenX >= pW || screenY < 0 || screenY >= pH)
                return;

            int lightColour = l.getLightValue(x0, y0);
            if(lightColour == 0)
                return;

            if(lb[screenX + screenY * pW] == Light.FULL)
                return;

            setLightMap(screenX, screenY, lightColour);

            if(x0 == x1 && y0 == y1) break; // We reached the destination

            e2 = 2 * err;
            if(e2 > -1 * dy) {
                err -= dy;
                x0 += sx;
            }

            if(e2 < dx) {
                err += dx;
                y0 += sy;
            }
        }
    }

    /**
     * Draws a line in any direction, between two points
     * @param x1 X-Position 1
     * @param y1 Y-Position 1
     * @param x2 X-Position 2
     * @param y2 Y-Position 2
     * @param colour Colour of line
     */
    public void drawLine(int x1, int y1, int x2, int y2, int colour) {
        int d = 0;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;
        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                setPixel(x, y, colour);
                if (x == x2) break;
                x += ix; d += dy2;
                if (d > dx)
                    y += iy; d -= dx2;
            }
        } else {
            while (true) {
                setPixel(x, y, colour);
                if (y == y2) break;
                y += iy; d += dx2;
                if (d > dy)
                    x += ix; d -= dy2;
            }
        }
    }

    /**
     * Draws a circle at a point
     * @param offX X-Position
     * @param offY Y-Position
     * @param radius Radius of circle
     * @param colour Colour of circle
     */
    public void drawCircle(int offX, int offY, int radius, int colour) {
        offX = offX + (radius);
        offY = offY + (radius);
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        while(x <= y) {
            setPixel(offX + x, offY + y, colour);
            setPixel(offX + x, offY - y, colour);
            setPixel(offX - x, offY + y, colour);
            setPixel(offX - x, offY - y, colour);
            setPixel(offX + y, offY + x, colour);
            setPixel(offX + y, offY - x, colour);
            setPixel(offX - y, offY + x, colour);
            setPixel(offX - y, offY - x, colour);
            if(d < 0) {
                d += 2 * x + 1;
            }else{
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        }
    }

    /**
     * Calculates the length of a string, in pixels
     * @param text Text to calculate
     * @return Length of text, in pixels
     */
    public int textLength(String text) {
        int offset = 0;

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);
            offset += font.getWidths()[unicode];
        }
        return offset;
    }

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
