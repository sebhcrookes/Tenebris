package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;

public class Rigidbody extends Component {

    public static final float FALL_SPEED = 10;

    private float fallDistance = 0;

    public Rigidbody(GameObject parent) {
        super("rigidbody", parent);
    }

    @Override
    public void update(EngineAPI api, float dt) {

        RectCollider rect = (RectCollider) parent.getComponent("rect_collider");

        if(rect != null) {
            if(!rect.isBaseColliding(api, (int) fallDistance + 2)) {
                applyGravity(dt);
            } else {
                fallDistance = 0;
            }
        } else applyGravity(dt);
    }

    private void applyGravity(float dt) {
        fallDistance += dt * Rigidbody.FALL_SPEED;
        this.parent.getPosition().y = parent.getPosition().y + fallDistance;
    }

    @Override
    public void render(EngineAPI api, Renderer r) {}
}
