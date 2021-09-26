package com.tenebris.engine.engine.objects.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.builtins.AABBComponent;

public class Square extends GameObject {

    public Square(String name, int posX, int posY, int width, int height) {
        super("test_square", name, null, posX, posY, width, height);

        AABBComponent aabb = new AABBComponent(this);
        aabb.getCollideWith().add("test_square");

        addComponent(aabb);
    }

    @Override
    public void update(EngineAPI api, float dt) {

    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawRect(posX, posY, width, height, 0xFFFFFFFF);
    }

    @Override
    public void collision(GameObject other) {

    }
}
