package com.game.engine.game;

import com.game.engine.engine.AbstractGame;
import com.game.engine.engine.GameContainer;
import com.game.engine.engine.Renderer;

public class GameManager extends AbstractGame {

    private GameContainer gc;
    public static final int AIR_COLOUR = 0xFF000000;
    public static final int TS = 16;

    public GameManager() {}

    @Override
    public void init(GameContainer gc) {}

    @Override
    public void update(GameContainer gc, float dt) {}

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawText("Test!", 10, 10, 0xFFDC143C);
    }

    public void start() {
        gc = new GameContainer(this);
        gc.start(this);
    }
}