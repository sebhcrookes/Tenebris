package com.game.engine.engine.gfx.temp;

import com.game.engine.engine.gfx.Image;

public class ImageData {

    public Image image;
    public int zDepth;
    public int offX, offY;

    public ImageData(Image image, int zDepth, int offX, int offY) {
        this.image = image;
        this.zDepth = zDepth;
        this.offX = offX;
        this.offY = offY;
    }
}