package com.tenebris.engine.engine.util;

public class Vector2 {

    public float x;
    public float y;

    public Vector2() {}

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }
}
