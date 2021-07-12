package com.game.engine.engine.gfx.temp;

import com.game.engine.engine.gfx.Light;

public class LightData {

    public Light light;
    public int posX, posY;

    public LightData(Light light, int posX, int posY) {
        this.light = light;
        this.posX = posX;
        this.posY = posY;
    }
}
