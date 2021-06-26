package com.game.engine.engine;

public class EngineSettings {

    private static String title = "game.exe";
    private static int width = 512;
    private static int height = 288;
    private static float scale = 2.0f;
    private static double updateCap = 1.0 / 60.0;
    private static boolean debug = false;
    private static boolean lockFPS = false;

    private static String iconPath = null;
    private static String cursorPath = null;

    public EngineSettings() {}

    public static String getTitle() {
        return title;
    }

    public static String getIconPath() { return iconPath; }

    public static String getCursorPath() { return cursorPath; }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public double getUpdateCap() {
        return updateCap;
    }

    public void setUpdateCap(double updateCap) {
        this.updateCap = updateCap;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isLockFPS() {
        return lockFPS;
    }

    public void setLockFPS(boolean lockFPS) {
        this.lockFPS = lockFPS;
    }
}
