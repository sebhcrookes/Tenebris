package com.tenebris.engine.engine.objects.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.RectCollider;
import com.tenebris.engine.engine.util.Vector2;

public class TestPlatform extends GameObject {

    public TestPlatform(int posX, int posY) {
        super("test_platform", new Vector2(posX, posY), new Vector2(16, 16));

        addComponent(new RectCollider(this));
    }

    @Override
    public void update(EngineAPI api, float dt) {

    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawRect((int) position.x, (int) position.y, (int) size.x, (int) size.y, 0xFFFFFFFF);
    }
}
