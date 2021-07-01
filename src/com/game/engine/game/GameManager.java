package com.game.engine.game;

import com.game.engine.engine.AbstractGame;
import com.game.engine.engine.GameContainer;
import com.game.engine.engine.Renderer;
import com.game.engine.engine.gui.GUIButton;
import com.game.engine.engine.position.Vector2;

public class GameManager extends AbstractGame {

    private GameContainer gc;
    public static final int AIR_COLOUR = 0xFF000000;
    public static final int TS = 16;

    public GUIButton button;

    public GameManager() {}

    @Override
    public void init(GameContainer gc) {
        button = new GUIButton();
        button.setBounds(new Vector2(0,0), 100, 9);
        button.setText("Hello!");
        button.pack();
        button.setClickConsumer(GameManager::clicked);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        button.update(gc, this, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        button.render(gc, r);
        r.drawText("Test!", 10, 10, 0xFFDC143C);
    }

    public void start() {
        gc = new GameContainer(this);
        gc.start(this);
    }

    private static void clicked(Object o) {
        System.out.println("epic");
    }
}