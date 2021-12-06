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
        fallDistance += dt * Rigidbody.FALL_SPEED;
        this.parent.getPosition().y = parent.getPosition().y + fallDistance;
    }

    @Override
    public void render(EngineAPI api, Renderer r) {}
}
