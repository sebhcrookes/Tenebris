package com.tenebris.engine.engine.core;

import com.tenebris.engine.engine.objects.Objects;
import com.tenebris.engine.engine.states.Game;
import com.tenebris.engine.engine.util.EngineSettings;

public class EngineAPI {

    private GameEngine engine;

    public EngineAPI() {}

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

    public Window getWindow() {
        return engine.getWindow();
    }

    public Input getInput() {
        return engine.getInput();
    }

    public int getFPS() {
        return engine.getFps();
    }

    public Renderer getRenderer() {
        return engine.getRenderer();
    }

    public EngineSettings getSettings() {
        return engine.getSettings();
    }

    public Game getGame() {
        return engine.getGame();
    }

    public Objects getObjects() {
        return engine.getObjects();
    }
}
