package com.game.engine.engine.states;

import com.game.engine.engine.core.EngineAPI;

public abstract class Game {

    private State state;
    private EngineAPI api;

    public abstract void init(EngineAPI api);

    public abstract void dispose();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.state != null)
            this.state.dispose(api);
        this.state = state;
        this.state.init(api);
    }

    public void setAPI(EngineAPI api) {
        this.api = api;
    }
}
