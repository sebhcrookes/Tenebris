package com.game.engine.engine.core.rendering;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.gfx.Colour;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.gfx.temp.LightData;

import java.util.ArrayList;
import java.util.Arrays;

public class LightRenderer {

    private Renderer r;
    private GameEngine engine;

    private ArrayList<LightData> tempLightData = new ArrayList<>();
    private int[] lightMap;
    private int[] lightBlock;

    public LightRenderer(GameEngine engine, Renderer r) {
        this.engine = engine;
        this.r = r;
    }

    public void initialise(int length) {
        lightMap = new int[length];
        lightBlock = new int[length];
    }

    public void process(int[] pixels) {
        if(engine.getSettings().isLightingEnabled()) { // Draw and blend lighting together
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
    }

    public void drawLight(Light light, int posX, int posY) {
        tempLightData.add(new LightData(light, posX, posY));
    }

    public void setLightMap(int x, int y, int value) {

        int screenWidth = engine.getSettings().getWidth();

        if(x < 0 || x >= screenWidth || y < 0 || y >= engine.getSettings().getHeight())
            return; // Don't render

        int baseColour = lightMap[x + y * screenWidth];

        int maxRed = Math.max((baseColour >> 16) & 0xff, (value >> 16) & 0xff);
        int maxGreen = Math.max((baseColour >> 8) & 0xff, (value >> 8) & 0xff);
        int maxBlue = Math.max(baseColour & 0xff, value & 0xff);

        lightMap[x + y * screenWidth] = (maxRed << 16 | maxGreen << 8 | maxBlue);
    }

    public void setLightBlock(int posX, int posY, int value) {

        int screenWidth = engine.getSettings().getWidth();

        if(posX < 0 || posX >= screenWidth || posY < 0 || posY >= engine.getSettings().getHeight())
            return; // Don't render

        if(r.getzBuffer()[posX + posY * screenWidth] > r.getzDepth())
            return;

        lightBlock[posX + posY * screenWidth] = value;
    }

    private void drawLightRequest(Light l, int offX, int offY) {

        offX -= r.getCamX();
        offY -= r.getCamY();

        for(int i = 0; i <= l.getDiameter(); i++) {
            drawLightLine(l, l.getRadius(), l.getRadius(), i, 0, offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), i, l.getDiameter(), offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), 0, i, offX, offY);
            drawLightLine(l, l.getRadius(), l.getRadius(), l.getDiameter(), i, offX, offY);
        }
    }

    private void drawLightLine(Light l, int x0, int y0, int x1, int y1, int offX, int offY) {

        int screenWidth = engine.getSettings().getWidth();
        int screenHeight = engine.getSettings().getHeight();

        int dx = Math.abs(x1 - x0); // Change in x
        int dy = Math.abs(y1 - y0); // Change in y

        // Determining which direction we're heading in
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;

        int err = dx - dy;
        int e2;

        while(true) { // Using Bresenham's line drawing algorithm

            int screenX = x0 - l.getRadius() + offX;
            int screenY = y0 - l.getRadius() + offY;

            if(screenX < 0 || screenX >= screenWidth || screenY < 0 || screenY >= screenHeight)
                return;

            int lightColour = l.getLightValue(x0, y0);
            if(lightColour == 0)
                return;

            if(lightBlock[screenX + screenY * screenWidth] == Light.FULL)
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

    public void clearTempLightData() {
        tempLightData.clear();
    }

    public void clearLighting(int ambientColour) {
        Arrays.fill(lightMap, ambientColour);
        Arrays.fill(lightBlock, Light.NONE);
    }
}
