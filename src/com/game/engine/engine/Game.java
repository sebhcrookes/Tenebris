package com.game.engine.engine;

public abstract class Game {

    private GameState state;

    public abstract void init();
    public abstract void dispose();

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
        this.state.init();
    }

}
