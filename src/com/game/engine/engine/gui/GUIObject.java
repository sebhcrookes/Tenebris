package com.game.engine.engine.gui;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.core.Renderer;

public abstract class GUIObject {

    protected int posX, posY;

    public abstract void update(GameEngine gc, float dt);
    public abstract void render(GameEngine gc, Renderer r);

}
