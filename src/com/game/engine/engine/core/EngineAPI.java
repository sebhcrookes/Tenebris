package com.game.engine.engine.core;

import com.game.engine.engine.states.Game;
import com.game.engine.engine.util.EngineSettings;

public class EngineAPI {

    private GameEngine engine;

    public EngineAPI() {
    }

    public void init(Game game, EngineSettings engineSettings) {
        this.engine = new GameEngine(game, engineSettings);
        this.engine.setAPI(this);
    }

    public void start() {
        this.engine.start();
    }

    public void stop() {
        this.engine.stop();
    }

    /**
     * <p><font color=#b13b3f> Warning: <br> </font>
     * This function does not call dispose() methods, <br> therefore the engine will abruptly halt.
     */
    public void forceStop() {
        this.engine.forceStop();
    }


    // API Methods

    public int getWidth() {
        return engine.settings.getWidth();
    }

    public void setWidth(int width) {
        engine.settings.setWidth(width);
    }

    public int getHeight() {
        return engine.settings.getHeight();
    }

    public void setHeight(int height) {
        engine.settings.setHeight(height);
    }

    public String getTitle() {
        return engine.settings.getTitle();
    }

    public void setTitle(String title) {
        engine.settings.setTitle(title);
    }

    public float getScale() {
        return engine.settings.getScale();
    }

    public Window getWindow() {
        return engine.window;
    }

    public Input getInput() {
        return engine.input;
    }

    public int getFPS() {
        return engine.fps;
    }

    public Renderer getRenderer() {
        return engine.renderer;
    }

    public EngineSettings getSettings() {
        return engine.settings;
    }

    public Game getGame() {
        return engine.game;
    }

    public void setClearColour(int clearColour) {
        engine.setClearColour(clearColour);
    }
}
