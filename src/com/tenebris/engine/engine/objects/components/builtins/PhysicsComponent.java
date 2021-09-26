package com.tenebris.engine.engine.objects.components.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.Component;

public class PhysicsComponent extends Component {

    private final GameObject parent;

    private double fallDistance = 0;
    private double fallSpeed = 9.81;

    public PhysicsComponent(GameObject parent) {
        this.tag = "physics";
        this.parent = parent;
        this.priority = 0;
    }

    public PhysicsComponent(GameObject parent, double fallSpeed) {
        this.tag = "physics";
        this.parent = parent;
        this.fallSpeed = fallSpeed;
    }

    @Override
    public void update(EngineAPI api, float dt) {
        if (!parent.isGrounded()) {
            fallDistance += fallSpeed * dt;
            parent.setPosY((int) (parent.getPosY() + fallDistance));
        } else {
            resetFallVelocity();
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        if (api.getSettings().isDebug()) {
            r.drawLine(parent.getPosX() + (parent.getWidth() / 2), parent.getPosY() + parent.getHeight() / 2,
                    parent.getPosX() + parent.getWidth() / 2, (int) (parent.getPosY() + (parent.getHeight() / 2) + fallDistance * 3),
                    0xFFFFFFFF);
        }
    }

    @Override
    public void collision(GameObject other) {

    }

    public double getFallSpeed() {
        return fallSpeed;
    }

    public void setFallSpeed(double fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public void resetFallVelocity() {
        this.fallDistance = 0.0;
    }

    public double getFallVelocity() {
        return fallDistance;
    }

    public void setFallVelocity(double fallVelocity) {
        this.fallDistance = fallVelocity;
    }
}
