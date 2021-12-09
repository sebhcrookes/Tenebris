package com.tenebris.engine.engine.objects.components;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.Raycast;
import com.tenebris.engine.engine.util.Vector2;

public class RectCollider extends Component {

    public RectCollider(GameObject parent) {
        super("rect_collider", parent);
    }

    @Override
    public void update(EngineAPI api, float dt) {

    }

    @Override
    public void render(EngineAPI api, Renderer r) {

    }

    public int baseToNearestObject(EngineAPI api, int castDistance) {

        for(int i = 0; i < parent.getSize().x; i++) {

            GameObject test = api.getObjects().raycast(new Vector2(parent.getPosition().x + i, parent.getPosition().y + parent.getSize().y), Raycast.DOWN, castDistance);
            if (test == null) continue;
            if (test.getComponent("rect_collider") == null) continue;

            return (int)(test.getPosition().y - (parent.getPosition().y + parent.getSize().y));
        }
        return -1;
    }

    public boolean isLeftColliding(EngineAPI api) {
        for(int i = 0; i < parent.getSize().y; i++) {

            GameObject test = api.getObjects().raycast(new Vector2(parent.getPosition().x, parent.getPosition().y + i), Raycast.LEFT, 3);
            if (test == null) continue;
            if (test.getComponent("rect_collider") == null) continue;

            return true;
        }
        return false;
    }

    public boolean isRightColliding(EngineAPI api) {
        for(int i = 0; i < parent.getSize().y; i++) {

            GameObject test = api.getObjects().raycast(new Vector2(parent.getPosition().x + parent.getSize().x, parent.getPosition().y + i), Raycast.RIGHT, 3);
            if (test == null) continue;
            if (test.getComponent("rect_collider") == null) continue;

            return true;
        }
        return false;
    }

    public boolean isTopColliding(EngineAPI api) {
        for(int i = 0; i < parent.getSize().y; i++) {

            GameObject test = api.getObjects().raycast(new Vector2(parent.getPosition().x + i, parent.getPosition().y), Raycast.UP, 3);
            if (test == null) continue;
            if (test.getComponent("rect_collider") == null) continue;

            return true;
        }
        return false;
    }
}
