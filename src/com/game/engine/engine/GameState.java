package com.game.engine.engine;

public abstract class GameState {
    public abstract void init();
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);
}