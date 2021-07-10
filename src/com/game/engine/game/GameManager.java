package com.game.engine.game;

import com.game.engine.engine.*;
import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.gfx.Light;
import com.game.engine.engine.position.Vector2;
import com.game.engine.engine.util.EngineSettings;
import com.game.engine.engine.State;

import java.awt.event.KeyEvent;

public class GameManager {

    private class TestState extends State {

        private Image background = new Image("/background.png");
        private Image block = new Image("/block.png");
        private Light light = new Light(100, 0xFF00FFFF);

        @Override
        public void init() {}

        @Override
        public void update(EngineAPI api, float dt) {
            block.setLightBlock(Light.FULL);
            api.setClearColour(0xFFFF00FF);

            if(api.getInput().isKeyDown(KeyEvent.VK_BACK_SPACE)) {
                api.stop();
            }
        }

        @Override
        public void render(EngineAPI api, Renderer r) {
            r.setzDepth(0);
            r.drawImage(background, 0, 0);
            r.drawImage(block, 100, 100);

            r.drawLight(light, new Vector2(api.getInput().getMouseX(), api.getInput().getMouseY()));
        }

        @Override
        public void dispose() {
            System.out.println("epic");
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
        EngineAPI api = new EngineAPI();
        api.init(new TestGame(), new EngineSettings());
        api.start();
    }
}