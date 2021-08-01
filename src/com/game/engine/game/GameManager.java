package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.states.Game;
import com.game.engine.engine.util.EngineSettings;

public class GameManager {

    public GameManager() {
    }

    public void start() {
        EngineAPI api = new EngineAPI();
        api.init(new TestGame(), new EngineSettings());
        api.start();
    }

    private class TestGame extends Game {

        @Override
        public void init(EngineAPI api) {
            this.setState(new GameState());
        }

        @Override
        public void dispose() {
        } // Called when application is exited
    }
}