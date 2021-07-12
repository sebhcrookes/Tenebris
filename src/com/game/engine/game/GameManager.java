package com.game.engine.game;

import com.game.engine.engine.*;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.util.EngineSettings;
import com.game.engine.engine.State;

public class GameManager {

    private class TestState extends State {

        @Override
        public void init() {}

        @Override
        public void update(EngineAPI api, float dt) {

        }

        @Override
        public void render(EngineAPI api, Renderer r) {

        }

        @Override
        public void dispose() {}
    }

    private class TestGame extends Game {

        @Override
        public void init() {
            this.setState(new TestState());
        }

        @Override
        public void dispose() {}
    }

    public GameManager() {}

    public void start() {
        EngineAPI api = new EngineAPI();
        api.init(new TestGame(), new EngineSettings());
        api.start();
        api.setClearColour(0xFF000000);
    }
}