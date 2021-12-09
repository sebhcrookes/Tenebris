package com.tenebris.engine.engine.objects.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.RectCollider;
import com.tenebris.engine.engine.objects.components.Rigidbody;
import com.tenebris.engine.engine.util.Vector2;

import java.awt.event.KeyEvent;

public class TestPlayer extends GameObject {

    public TestPlayer(int posX, int posY) {
        super("test_player", new Vector2(posX, posY), new Vector2(16, 16));

        addComponent(new RectCollider(this));
        addComponent(new Rigidbody(this));
    }

    @Override
    public void update(EngineAPI api, float dt) {
        Rigidbody rigidbody = (Rigidbody) getComponent("rigidbody");

        if(api.getInput().isKey(KeyEvent.VK_RIGHT))
            rigidbody.getVelocity().x = 1;
        if(api.getInput().isKey(KeyEvent.VK_LEFT))
            rigidbody.getVelocity().x = -1;
        if(api.getInput().isKeyDown(KeyEvent.VK_UP)) {
            rigidbody.getVelocity().y = (float) -3.14;
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawRect((int) position.x, (int) position.y, (int) size.x, (int) size.y, 0xFFFFFFFF);
    }
}
