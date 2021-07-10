package com.game.engine.engine;

import com.game.engine.engine.EngineAPI;
import com.game.engine.engine.Renderer;

public abstract class State {
    public abstract void init();
    public abstract void update(EngineAPI api, float dt);
    public abstract void render(EngineAPI api, Renderer r);
    public abstract void dispose(); // Runs when window is closed or when program halts execution
}