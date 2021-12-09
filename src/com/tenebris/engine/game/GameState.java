package com.tenebris.engine.game;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.ObjectHandle;
import com.tenebris.engine.engine.objects.builtins.TestPlatform;
import com.tenebris.engine.engine.objects.builtins.TestPlayer;
import com.tenebris.engine.engine.states.State;

public class GameState extends State {

    @Override
    public void init(EngineAPI api) {
        api.getRenderer().setClearColour(0xFF18191A);
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
    public void dispose(EngineAPI api) {
        // Dispose is called when state is changed or when application is closed/exits
    }
}