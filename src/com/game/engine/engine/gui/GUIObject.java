package com.game.engine.engine.gui;

import com.game.engine.engine.GameContainer;
import com.game.engine.engine.util.GameObject;
import com.game.engine.engine.Renderer;
import com.game.engine.game.GameManager;

public abstract class GUIObject extends GameObject {

    @Override
    public abstract void update(GameContainer gc, float dt);

    @Override
    public abstract void render(GameContainer gc, Renderer r);

}
