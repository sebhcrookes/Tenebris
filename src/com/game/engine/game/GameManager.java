package com.game.engine.game;

import com.game.engine.engine.*;
import com.game.engine.engine.util.EngineSettings;

public class GameManager {

    private class TestState extends GameState {

        @Override
        public void init() {

        }

        @Override
        public void update(GameContainer gc, float dt) {

        }

        @Override
        public void render(GameContainer gc, Renderer r) {
            r.drawText("Hello!", 0, 0, 0xFFFFFFFF);
        }
    }

    private class TestGame extends Game {

        @Override
        public void init() {
            this.setState(new TestState());
        }

        @Override
        public void dispose() {

        }
    }

    public GameManager() {}
    public void start() {
        GameContainer gc = new GameContainer(new TestGame(), new EngineSettings());
        gc.start();
    }
}