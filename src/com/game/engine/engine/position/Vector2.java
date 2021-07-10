package com.game.engine.engine.position;

public class Vector2 {

    public int X, Y;

    public Vector2(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public void setX(int X) {
        this.X = X;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    public void incrementX(int incrementBy) {
        this.X += incrementBy;
    }

    public void incrementY(int incrementBy) {
        this.Y += incrementBy;
    }
}
