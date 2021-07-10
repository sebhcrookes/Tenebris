package com.game.engine.engine.gui;

import com.game.engine.engine.GameEngine;
import com.game.engine.engine.util.GameObject;
import com.game.engine.engine.Renderer;

public abstract class GUIObject extends GameObject {

    @Override
    public abstract void update(GameEngine gc, float dt);

    @Override
    public abstract void render(GameEngine gc, Renderer r);

}
