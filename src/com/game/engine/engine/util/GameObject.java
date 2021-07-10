package com.game.engine.engine.util;

import com.game.engine.engine.GameEngine;
import com.game.engine.engine.Renderer;
import com.game.engine.engine.position.Vector2;

public abstract class GameObject {

    protected String tag;
    protected Vector2 position;
    protected int width, height;
    protected boolean dead = false;

    public abstract void update(GameEngine gc, float dt);
    public abstract void render(GameEngine gc, Renderer r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
