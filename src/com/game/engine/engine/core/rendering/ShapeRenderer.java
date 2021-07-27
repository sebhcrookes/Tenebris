package com.game.engine.engine.core.rendering;

import com.game.engine.engine.core.GameEngine;

public class ShapeRenderer {

    private Renderer r;
    private GameEngine engine;

    public ShapeRenderer(GameEngine engine, Renderer r) {
        this.engine = engine;
        this.r = r;
    }

    public void drawRect(int posX, int posY, int width, int height, int colour) {

        posX -= r.getCamX();
        posY -= r.getCamY();

        for(int y = 0; y <= height; y++) { // Drawing the vertical sides
            r.setPixel(posX, y + posY, colour);
            r.setPixel(posX + width, y + posY, colour);
        }

        for(int x = 0; x <= width; x++) { // Drawing the horizontal sides
            r.setPixel(posX + x, posY, colour);
            r.setPixel(posX + x, posY + height, colour);
        }
    }

    public void drawFillRect(float posX, float posY, int width, int height, int colour) {

        posX -= r.getCamX();
        posY -= r.getCamY();

        for (int y = 0; y <= height; y++) { // Loops through every pixel in the rect and sets the pixel
            for (int x = 0; x <= width; x++) {
                r.setPixel((int)(x + posX), (int)(y + posY), colour);
            }
        }
    }

    public void drawLine(int x1, int y1, int x2, int y2, int colour) {
        int d = 0;
        int dx = Math.abs(x2 - x1); // Change in x
        int dy = Math.abs(y2 - y1); // Change in y
        int dx2 = 2 * dx; // Double dx
        int dy2 = 2 * dy; // Double dy

        // Determining the direction we're heading in
        int ix = x1 < x2 ? 1 : -1;
        int iy = y1 < y2 ? 1 : -1;

        int x = x1; // Start x-position
        int y = y1; // Start y-position

        if (dx >= dy) {
            while (true) {
                r.setPixel(x, y, colour);
                if (x == x2) break; // We reached our destination
                x += ix;
                d += dy2;
                if (d > dx)
                    y += iy;
                d -= dx2;
            }
        } else {
            while (true) {
                r.setPixel(x, y, colour);
                if (y == y2) break; // We reached our destination
                y += iy;
                d += dx2;
                if (d > dy)
                    x += ix;
                d -= dy2;
            }
        }
    }

    public void drawCircle(int offX, int offY, int radius, int colour) {
        offX = offX + (radius);
        offY = offY + (radius);
        int d = (5 - radius * 4)/4;
        int x = 0;
        int y = radius;

        while(x <= y) {
            r.setPixel(offX + x, offY + y, colour);
            r.setPixel(offX + x, offY - y, colour);
            r.setPixel(offX - x, offY + y, colour);
            r.setPixel(offX - x, offY - y, colour);
            r.setPixel(offX + y, offY + x, colour);
            r.setPixel(offX + y, offY - x, colour);
            r.setPixel(offX - y, offY + x, colour);
            r.setPixel(offX - y, offY - x, colour);
            if(d < 0) {
                d += 2 * x + 1;
            }else{
                d += 2 * (x - y) + 1;
                y--;
            }
            x++;
        }
    }
}
