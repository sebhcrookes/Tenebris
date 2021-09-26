package com.tenebris.engine.engine.gfx;

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

    public static float getLiteralRed(int colour) {
        return ((colour >> 16) & 0xFF);
    }

    public static float getLiteralGreen(int colour) {
        return ((colour >> 8) & 0xFF);
    }

    public static float getLiteralBlue(int colour) {
        return (colour & 0xFF);
    }

    public static int getColour(float a, float r, float g, float b) {
        return (int) (a) << 24 | (int) (r) << 16 | (int) (g) << 8 | (int) (b);
    }

    public static int getColour(float r, float g, float b) {
        return (int) (r) << 16 | (int) (g) << 8 | (int) (b);
    }

    private static int getSmallColour(float a, float r, float g, float b) {
        return (int) (a) << 24 | (int) (r * 255) << 16 | (int) (g * 255) << 8 | (int) (b * 255);
    }

    private static int alpha(int color) {
        // shift bits: 0xDEADBEEF -> 0x000000DE
        // and apply mask: 0x000000DE -> 0x000000DE
        return color >> 24 & 0xFF;
    }

    private static int red(int color) {
        // shift bits: 0xDEADBEEF -> 0x0000DEAD
        // and apply mask: 0x0000DEAD -> 0x000000AD
        return color >> 16 & 0xFF;
    }

    private static int green(int color) {
        // shift bits: 0xDEADBEEF -> 0x00DEADBE
        // and apply mask: 0x00DEADBE -> 0x000000BE
        return color >> 8 & 0xFF;
    }

    private static int blue(int color) {
        // shift bits: 0xDEADBEEF -> 0xDEADBEEF
        // and apply mask: 0xDEADBEEF -> 0x000000EF
        return color & 0xFF;
    }

    public static int mixColours(int color1, int color2, float step) {
        // Cutoff to range between 0.0f and 1.0f
        step = Math.max(Math.min(step, 1.0f), 0.0f);

        // Calculate difference between alpha, red, green and blue channels
        int deltaAlpha = alpha(color2) - alpha(color1);
        int deltaRed = red(color2) - red(color1);
        int deltaGreen = green(color2) - green(color1);
        int deltaBlue = blue(color2) - blue(color1);

        // Result channel lies between first and second colors channel
        int resultAlpha = (int) (alpha(color1) + (deltaAlpha * step));
        int resultRed = (int) (red(color1) + (deltaRed * step));
        int resultGreen = (int) (green(color1) + (deltaGreen * step));
        int resultBlue = (int) (blue(color1) + (deltaBlue * step));

        // Cutoff to ranges between 0 and 255
        resultAlpha = Math.max(Math.min(resultAlpha, 255), 0);
        resultRed = Math.max(Math.min(resultRed, 255), 0);
        resultGreen = Math.max(Math.min(resultGreen, 255), 0);
        resultBlue = Math.max(Math.min(resultBlue, 255), 0);

        // Combine results
        return resultAlpha << 24 | resultRed << 16 | resultGreen << 8 | resultBlue;
    }
}