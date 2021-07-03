package com.game.engine.engine.util;

public class EngineSettings {

    /**
     * EngineSettings class allows for simple modification to engine-related settings
     */

    private String title = "game.engine";
    private int width = 512;
    private int height = 288;
    private float scale = 2.0f;
    private double updateCap = 1.0 / 60.0;
    private boolean debug = false;
    private boolean lockFPS = false;

    private String iconPath = null;
    private String cursorPath = null;

    public EngineSettings() {}

    public String getTitle() {
        return title;
    }

    public String getIconPath() {
        return iconPath;
    }

    public String getCursorPath() {
        return cursorPath;
    }

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
