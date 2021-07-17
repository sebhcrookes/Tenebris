package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.states.Game;
import com.game.engine.engine.states.State;
import com.game.engine.engine.util.EngineSettings;

public class GameManager {

    private class TestState extends State {

        private Image background = new Image("/background.png");
        private Image block = new Image("/block.png");
        private Light light = new Light(100, 0xFF00FFFF);

        private Light redLight = new Light(100, 0xFFFFFF00);

        @Override
        public void init() {

        }

        @Override
        public void update(EngineAPI api, float dt) {
            block.setLightBlock(Light.FULL);
            api.setClearColour(0xFFFF00FF);
        }

        @Override
        public void render(EngineAPI gc, Renderer r) {
            r.setzDepth(0);
            r.drawImage(background, 0, 0);
            r.drawImage(block, 100, 100);

            r.drawLight(redLight, 0, 0);
            r.drawLight(light, gc.getInput().getMouseX(), gc.getInput().getMouseY());
        }

        @Override
        public void dispose() {

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
        EngineAPI gc = new EngineAPI();
        gc.init(new TestGame(), new EngineSettings());
        gc.start();
    }
}