package com.tenebris.engine.engine.objects.components.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.Component;
import com.tenebris.engine.engine.objects.components.Physics;

public class PhysicsComponent extends Component {

    private GameObject parent;

    private double fallDistance = 0;
    private double fallSpeed = 9.81;

    public PhysicsComponent(GameObject parent) {
        this.tag = "physics";
        this.parent = parent;
    }

    public PhysicsComponent(GameObject parent, double fallSpeed) {
        this.tag = "physics";
        this.parent = parent;
        this.fallSpeed = fallSpeed;
    }

    @Override
    public void update(EngineAPI api, float dt) {
        fallDistance += fallSpeed * dt;

        parent.setPosY(parent.getPosY() + (int)fallDistance);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        // If debug is enabled, maybe render an arrow indicating the downwards force?
    }

    @Override
    public void collision(GameObject other) {

    }

    public void resetFallVelocity() {
        this.fallDistance = 0;
    }
}
