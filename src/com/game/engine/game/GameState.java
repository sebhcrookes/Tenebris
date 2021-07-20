package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.states.State;

public class GameState extends State {

    @Override
    public void init(EngineAPI api) {
        api.setClearColour(0xFF18191A);
    }

    @Override
    public void update(EngineAPI api, float dt) {
        // Update is called on update tick
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        // Render is called once per frame
    }

    @Override
    public void dispose() {
        // Dispose is called when state is changed or when application is closed/exits
    }
}