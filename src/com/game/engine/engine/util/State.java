package com.game.engine.engine.util;

import com.game.engine.engine.GameContainer;
import com.game.engine.engine.Renderer;

public abstract class State {
    public abstract void init();
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    public abstract void dispose();
}