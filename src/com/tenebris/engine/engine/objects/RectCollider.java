package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
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

    public boolean isBaseColliding(EngineAPI api, int castDistance) {

        for(int i = 0; i < parent.getSize().x; i++) {

            GameObject test = api.getObjects().raycast(new Vector2(parent.getPosition().x + i, parent.getPosition().y + parent.getSize().y + 1), Raycast.DOWN, castDistance);
            if (test == null) continue;
            if (test.getComponent("rect_collider") == null) continue;

            return true;
        }
        return false;
    }
}
