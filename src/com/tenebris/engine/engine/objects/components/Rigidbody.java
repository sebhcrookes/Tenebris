package com.tenebris.engine.engine.objects.components;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.util.Vector2;

public class Rigidbody extends Component {

    public static final float FALL_SPEED = 10;

    private Vector2 velocity = new Vector2(0, 0);

    private boolean collisionLast = false;

    public Rigidbody(GameObject parent) {
        super("rigidbody", parent);
    }

    @Override
    public void update(EngineAPI api, float dt) {

        RectCollider rect = (RectCollider) parent.getComponent("rect_collider");

        if(rect != null) {
            int collision = rect.baseToNearestObject(api, (int) velocity.y + 3);
            if(collision < 0) {
                applyGravity(dt);
                collisionLast = false;
            } else if(!collisionLast) {
                parent.getPosition().y += collision;
                velocity.y = 0;
                collisionLast = true;
            } else {
                velocity.y = 0;
                collisionLast = true;
            }
        } else applyGravity(dt);

        if(rect != null) {
            if(rect.isLeftColliding(api)) {
                if(velocity.x < 0)
                    velocity.x = 0;
            }
            if (rect.isRightColliding(api)) {
                if(velocity.x > 0)
                    velocity.x = 0;
            }
            if(rect.isTopColliding(api)) {
                if(velocity.y < 0)
                    velocity.y = 0;
            }
        }

        parent.getPosition().x += velocity.x;

        velocity.x /= 1.25;
    }

    private void applyGravity(float dt) {
        velocity.y += dt * Rigidbody.FALL_SPEED;
        this.parent.getPosition().y = parent.getPosition().y + velocity.y;
    }

    private void applyGravity(float dt, int maxDistance) {
        if(dt * Rigidbody.FALL_SPEED >= maxDistance) {
            this.parent.getPosition().y += maxDistance;
            return;
        }
        velocity.y += dt * Rigidbody.FALL_SPEED;
        this.parent.getPosition().y = parent.getPosition().y + velocity.y;
    }

    @Override
    public void render(EngineAPI api, Renderer r) {}

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
