package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.states.Game;
import com.game.engine.engine.util.EngineSettings;

public class GameManager {

    private class TestGame extends Game {

        @Override
        public void init(EngineAPI api) {
            this.setState(new GameState());
        }

        @Override
        public void dispose() {} // Called when application is exited
    }

    public GameManager() {}

    public void start() {
        EngineAPI gc = new EngineAPI();
        gc.init(new TestGame(), new EngineSettings());
        gc.start();
    }
}