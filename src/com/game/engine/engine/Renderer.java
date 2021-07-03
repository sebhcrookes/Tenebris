package com.game.engine.engine;

import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.ImageRequest;
import com.game.engine.engine.position.Vector2;
import com.game.engine.game.GameManager;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.game.engine.engine.gfx.Font;

public class Renderer {

    private GameContainer gc;
    private GameManager gm;

    private ArrayList<ImageRequest> imageRequest = new ArrayList<>();

    private int pW, pH;
    private int[] p;
    private int[] zb;

    private float camX, camY;
    private int zDepth = 0;

    public Font font = Font.STANDARD;
    private boolean processing = false;

    public Renderer(GameContainer gc) {

        this.gc = gc;

        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zb = new int[p.length];
    }

    public void clear() {
        Arrays.fill(p, gc.clearColour);
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

        for(int i = 0; i < imageRequest.size(); i++) {
            ImageRequest iR = imageRequest.get(i);
            setzDepth(iR.zDepth);
            drawImage(iR.image, iR.offX, iR.offY);
        }

        imageRequest.clear();
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
        drawText(text, position.getPosX(), position.getPosY(), colour);
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
                setPixel(x + offX,y + offY, image.getPixels()[x + y * image.getWidth()]);
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
     * @param offX X-Position
     * @param offY Y-Position
     * @param width Width
     * @param height Height
     * @param colour Colour of rectangle
     */
    public void drawFillRect(int offX, int offY, int width, int height, int colour) {
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                setPixel(x + offX, y + offY, colour);
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
        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;
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
