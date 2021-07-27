package com.game.engine.engine.core.rendering;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.temp.ImageData;

import java.util.ArrayList;

public class ImageRenderer {

    private Renderer r;
    private GameEngine engine;

    private ArrayList<ImageData> tempImageData = new ArrayList<>();

    public ImageRenderer(GameEngine engine, Renderer r) {
        this.engine = engine;
        this.r = r;
    }

    public void process() {
        // Sort our image data from back to front
        tempImageData.sort((i0, i1) -> {
            if (i0.zDepth < i1.zDepth) {
                return -1;
            }
            if (i0.zDepth < i1.zDepth) {
                return 1;
            }
            return 0;
        });

        for (ImageData iR : tempImageData) {
            r.setzDepth(iR.zDepth); // Set the zDepth to the value of the next image
            drawImage(iR.image, iR.offX, iR.offY); // Draw the image
        }
    }


    public void drawImage(Image image, int posX, int posY) {

        posX -= r.getCamX();
        posY -= r.getCamY();

        if(image.isAlpha() && !r.isProcessing()) // Process our alpha images and layer them correctly
            tempImageData.add(new ImageData(image, r.getzDepth(), posX, posY));

        for(int y = 0; y < image.getHeight(); y++) {
            for(int x = 0; x < image.getWidth(); x++) {
                r.setPixel(x + posX,y + posY, image.getPixels()[(x) + (y) * image.getWidth()]);
                if(engine.getSettings().isLightingEnabled())
                    r.setLightBlock(x + posX, y + posY, image.getLightBlock()); // Set the pixel to a light-block if it blocks light
            }
        }
    }

    public void clearTempImageData() {
        tempImageData.clear();
    }
}
