package com.game.engine.engine.ui;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.util.types.Size;

public abstract class UIComponent {

    protected float posX, posY;
    protected Size size;
    protected String tag;

    public UIComponent() {
        this.posX = 0;
        this.posY = 0;
        this.size = new Size(1, 1);
        this.tag = "";
    }

    public abstract void update(EngineAPI api, float dt);
    public abstract void render(EngineAPI api, Renderer r);

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
