package com.game.engine.game;

import com.game.engine.engine.*;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.util.EngineFile;
import com.game.engine.engine.util.EngineSettings;
import com.game.engine.engine.util.State;

public class GameManager {

    private class TestState extends State {

        private Image background = new Image("/background.png");
        private Image block = new Image("/block.png");
        private Light light = new Light(100, 0xFF00FFFF);

        @Override
        public void init() {

        }

        @Override
        public void update(GameContainer gc, float dt) {
            block.setLightBlock(Light.FULL);
            gc.clearColour = 0xFFFF00FF;
        }

        @Override
        public void render(GameContainer gc, Renderer r) {
            r.setzDepth(0);
            r.drawImage(background, 0, 0);
            r.drawImage(block, 100, 100);

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
        GameContainer gc = new GameContainer(new TestGame(), new EngineSettings());
        gc.start();
    }
}