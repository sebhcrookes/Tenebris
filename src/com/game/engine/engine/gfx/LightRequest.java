package com.game.engine.engine.gfx;

import com.game.engine.engine.position.Vector2;

public class LightRequest {

    public Light light;
    public Vector2 position;

    public LightRequest(Light light, Vector2 position) {
        this.light = light;
        this.position = position;
    }
}
