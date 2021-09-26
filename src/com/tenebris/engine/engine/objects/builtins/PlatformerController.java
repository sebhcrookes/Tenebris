package com.tenebris.engine.engine.objects.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.gfx.Image;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.builtins.AABBComponent;
import com.tenebris.engine.engine.objects.components.builtins.PhysicsComponent;
import com.tenebris.engine.engine.objects.components.builtins.PlatformerComponent;

import java.awt.event.KeyEvent;

public class PlatformerController extends GameObject {

    public PlatformerController(String name, Image image, int posX, int posY) {
        super("2d_platformer", name, image, posX, posY, 16, 16);

        AABBComponent aabb = new AABBComponent(this);

        this.addComponent(aabb);

        this.addComponent(new PlatformerComponent(this));
        aabb.addCollision("test_square");
        this.addComponent(new PhysicsComponent(this));

        PlatformerComponent platformerComponent = (PlatformerComponent) this.findComponent("platformer");

        platformerComponent.addJumpBind(KeyEvent.VK_SPACE);
        platformerComponent.addJumpBind(KeyEvent.VK_W);
        platformerComponent.addLeftBind(KeyEvent.VK_A);
        platformerComponent.addRightBind(KeyEvent.VK_D);
    }

    @Override
    public void update(EngineAPI api, float dt) {

    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        if (this.image == null) {
            r.drawRect(posX, posY, width, height, 0xFFFFFFFF);
        } else {
            r.drawImage(image, posX, posY);
        }
    }

    @Override
    public void collision(GameObject other) {

    }
}
