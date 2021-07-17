package com.game.engine.engine.states;

public abstract class Game {

    private State state;

    public abstract void init();
    public abstract void dispose();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if(this.state != null)
            this.state.dispose();
        this.state = state;
        this.state.init();
    }

}
