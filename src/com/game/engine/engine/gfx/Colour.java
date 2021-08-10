    package com.game.engine.engine.gfx;

public class Colour {

    public static float getAlpha(int colour) {
        return ((colour >> 24) & 0xFF);
    }

    public static float getRed(int colour) {
        return ((colour >> 16) & 0xFF) / 255F;
    }

    public static float getGreen(int colour) {
        return ((colour >> 8) & 0xFF) / 255F;
    }

    public static float getBlue(int colour) {
        return (colour & 0xFF) / 255F;
    }

    public static int getColour(float a, float r, float g, float b) {
        return (int) (a) << 24 | (int) (r * 255f) << 16 | (int) (g * 255f) << 8 | (int) (b * 255f);
    }

    public static int getColour(float r, float g, float b) {
        return (int) (r) << 16 | (int) (g) << 8 | (int) (b);
    }
}
