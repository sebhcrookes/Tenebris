package com.tenebris.engine.game;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.gfx.Image;
import com.tenebris.engine.engine.states.State;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameState extends State {

    Image image = new Image("res://tenebris_banner.png");

    @Override
    public void init(EngineAPI api) {
        api.setClearColour(0xFF18191A);
    }

    @Override
    public void update(EngineAPI api, float dt) {
        // Update is called on update tick
        if(api.getInput().isKey(KeyEvent.VK_RIGHT)) {
            api.getRenderer().setCamX(api.getRenderer().getCamX() + 1);
        }
        if(api.getInput().isKey(KeyEvent.VK_LEFT)) {
            api.getRenderer().setCamX(api.getRenderer().getCamX() - 1);
        }

        if(api.getInput().isKey(KeyEvent.VK_UP)) {
            api.getRenderer().setCamY(api.getRenderer().getCamY() - 1);
        }

        if(api.getInput().isKey(KeyEvent.VK_DOWN)) {
            api.getRenderer().setCamY(api.getRenderer().getCamY() + 1);
        }

        if(api.getInput().isButtonDown(MouseEvent.BUTTON1)) {
            System.out.println(api.getRenderer().inViewFrustum(api.getInput().getMouseX(), api.getInput().getMouseY()));
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        // Render is called once per frame
        r.drawImage(image, 100, -1);
        //r.drawFillRect(0, 0, api.getWidth(), api.getHeight(), 0xFFFFFFFF);
    }

    @Override
    public void dispose(EngineAPI api) {
        // Dispose is called when state is changed or when application is closed/exits
    }
}