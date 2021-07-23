package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.states.State;

import java.lang.management.ManagementFactory;

public class GameState extends State {

    @Override
    public void init(EngineAPI api) {
        api.setClearColour(0xFF18191A);

        com.sun.management.OperatingSystemMXBean os = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println(os.getTotalPhysicalMemorySize());
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