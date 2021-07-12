package com.game.engine.engine.gui;

import com.game.engine.engine.GameEngine;
import com.game.engine.engine.Renderer;

public abstract class GUIObject {

    protected int posX, posY;

    public abstract void update(GameEngine gc, float dt);
    public abstract void render(GameEngine gc, Renderer r);

}
