package com.game.engine.engine.states;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;

public abstract class State {
    public abstract void init(EngineAPI api); // Runs when state is initialised

    public abstract void update(EngineAPI api, float dt); // Update is called once per

    public abstract void render(EngineAPI api, Renderer r); // Runs before frames are drawn to window (for rendering)

    public abstract void dispose(EngineAPI api); // Runs when window is closed or when program halts execution
}