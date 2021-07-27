package com.game.engine.engine.core.rendering;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.gfx.*;
import com.game.engine.engine.util.Logger;

import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class Renderer { // Tenebris Renderer

    private GameEngine engine;

    // Renderers
    private ShapeRenderer shapeRenderer;
    private TextRenderer textRenderer;
    private ImageRenderer imageRenderer;
    private LightRenderer lightRenderer;

    private int screenWidth, screenHeight;
    private int[] pixels;
    private int[] zBuffer;

    private float camX, camY;
    private int zDepth = 0;

    private int ambientColour = 0xFF555555;

    private boolean processing = false;

    public Renderer(GameEngine engine) {
        this.engine = engine;

        shapeRenderer = new ShapeRenderer(engine,this);
        textRenderer = new TextRenderer(engine,this);
        imageRenderer = new ImageRenderer(engine,this);
        lightRenderer = new LightRenderer(engine,this);

        screenWidth = this.engine.getSettings().getWidth();
        screenHeight = this.engine.getSettings().getHeight();
        pixels = ((DataBufferInt) this.engine.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zBuffer = new int[pixels.length];

        if(this.engine.getSettings().isLightingEnabled()) { // Initialise the lightmap arrays
            lightRenderer.initialise(pixels.length);
        }
        Logger.log(Logger.TENEBRIS_INFO, "Renderer initialised");
    }


    //================================================================================
    // Direct pixel-modification functions
    //================================================================================

    public void clear() {
        Arrays.fill(pixels, engine.getClearColour());
        Arrays.fill(zBuffer, 0);
        if(engine.getSettings().isLightingEnabled()) {
            lightRenderer.clearLighting(ambientColour);
        }
    }

    public void process() {
        processing = true;

        imageRenderer.process();
        lightRenderer.process(pixels);

        imageRenderer.clearTempImageData();
        lightRenderer.clearTempLightData();
        processing = false;
    }

    /**
     * Sets a pixel to a value on the window at a specific location
     * @param x X-Position
     * @param y Y-Position
     * @param value Colour of pixel
     */
    public void setPixel(int x, int y, int value) {
        int alpha = (int)Colour.getAlpha(value); // Get the alpha value from the colour

        if((x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) || alpha == 0)
            return; // Don't render pixels off-screen or with an alpha of 0

        int pixelIndex = x + y * screenWidth;

        if(zBuffer[pixelIndex] > zDepth)
            return;

        zBuffer[pixelIndex] = zDepth;

        if(alpha == 255) // Render a solid colour (with alpha 255)
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

    /**
     * Draws text to the window at a specific location
     * @param text Text to draw
     * @param posX X-Position
     * @param posY Y-Position
     * @param colour Colour of the text
     */
    public void drawText(String text, int posX, int posY, int colour) { textRenderer.drawText(text, posX, posY, colour); }

    /**
     * Draws an image to the window at a specific location
     *
     * @param image Image to draw
     * @param posX X-Position
     * @param posY Y-Position
     */
    public void drawImage(Image image, int posX, int posY) { imageRenderer.drawImage(image, posX, posY); }

    /**
     * Draws a rectangle to the window at a specific location
     * @param posX X-Position
     * @param posY Y-Position
     * @param width Width
     * @param height Height
     * @param colour Colour of rectangle
     */
    public void drawRect(int posX, int posY, int width, int height, int colour) { shapeRenderer.drawRect(posX, posY, width, height, colour); }

    /**
     * Draws a full rectangle to the window at a specific location
     *
     * @param posX x-position of rect on screen
     * @param posY y-position of rect on screen
     * @param width width of rect
     * @param height height of rect
     * @param colour colour of rect
     */
    public void drawFillRect(float posX, float posY, int width, int height, int colour) { shapeRenderer.drawFillRect(posX, posY, width, height, colour); }

    /**
     * Draws a line in any direction to the window between two points, using
     * Bresenham's line drawing algorithm
     *
     * @param x1 X-Position 1
     * @param y1 Y-Position 1
     * @param x2 X-Position 2
     * @param y2 Y-Position 2
     * @param colour Colour of line
     */
    public void drawLine(int x1, int y1, int x2, int y2, int colour) { shapeRenderer.drawLine(x1, y1, x2, y2, colour); }

    /**
     * Draws a circle to the window at a specific location
     *
     * @param posX X-Position
     * @param posY Y-Position
     * @param radius Radius of circle
     * @param colour Colour of circle
     */
    public void drawCircle(int posX, int posY, int radius, int colour) { shapeRenderer.drawCircle(posX, posY, radius, colour); }


    //================================================================================
    // Light-related functions
    //================================================================================

    /**
     * Draws a light to the window at a specific location
     *
     * @param light light to draw
     * @param posX x-position of light on screen
     * @param posY y-position of light on screen
     */
    public void drawLight(Light light, int posX, int posY) { lightRenderer.drawLight(light, posX, posY); }
    public void setLightMap(int x, int y, int value) { lightRenderer.setLightMap(x, y, value); }
    public void setLightBlock(int posX, int posY, int value) { lightRenderer.setLightBlock(posX, posY, value); }

    //================================================================================
    // Getters and Setters
    //================================================================================

    public Font getFont() {
        return textRenderer.getFont();
    }
    public void setFont(Font font) {
        textRenderer.setFont(font);
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

    public boolean isProcessing() {
        return processing;
    }

    public int[] getzBuffer() {
        return zBuffer;
    }
}