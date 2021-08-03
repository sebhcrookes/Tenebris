package com.game.engine.game;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.states.State;
import com.game.engine.engine.util.PropertiesFile;
import com.game.engine.engine.util.terminal.Console;

import java.awt.event.KeyEvent;

public class GameState extends State {

    @Override
    public void init(EngineAPI api) {
        PropertiesFile p = new PropertiesFile("ewoijsfloksdj");
        api.setClearColour(0xFF18191A);
    }

    @Override
    public void update(EngineAPI api, float dt) {
        // Update is called on update tick
        if(api.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
            Console.println("<purple>Input - <reset>Space key pressed!");
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        // Render is called once per frame
        r.drawText(String.valueOf(api.getFPS()), 0, 0, 0xFFFFFFFF);
    }

    @Override
    public void dispose(EngineAPI api) {
        // Dispose is called when state is changed or when application is closed/exits
    }
}