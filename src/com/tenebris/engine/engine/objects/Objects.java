package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.GameEngine;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.util.Vector2;

import java.util.ArrayList;

public class Objects {

    private GameEngine engine;

    private ArrayList<GameObject> objects;

    public Objects(GameEngine engine) {
        this.engine = engine;
        objects = new ArrayList<>();
    }

    public void update(EngineAPI api, float dt) {
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).update(api, dt);
            objects.get(i).updateComponents(api, dt);
        }
    }

    public void render(EngineAPI api, Renderer r) {
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).render(api, r);
            objects.get(i).renderComponents(api, r);
        }
    }

    public ObjectHandle add(GameObject object) {
        objects.add(object);
        return new ObjectHandle(object.getUUID());
    }

    public GameObject get(ObjectHandle handle) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getUUID().equals(handle.getUUID())) {
                return objects.get(i);
            }
        }

        return null;
    }

    private GameObject getObjectAt(Vector2 position) {
        for (GameObject object : objects) {

            int top = (int) object.position.y;
            int bottom = (int) object.position.y + (int) object.size.y;

            int left = (int) object.position.x;
            int right = (int) object.position.x + (int) object.size.x;

            if ((int) position.x > left && (int) position.x < right && (int) position.y > top && (int) position.y < bottom) {
                return object;
            }
        }
        return null;
    }

    public GameObject raycast(Vector2 position, int direction, int distance) {

        for(int i = 0; i < distance; i++) {
            GameObject collision = null;
            switch(direction) {
                case Raycast.DOWN:  collision = getObjectAt(new Vector2(position.x, position.y + i)); break;
                case Raycast.UP:    collision = getObjectAt(new Vector2(position.x, position.y - i)); break;
                case Raycast.LEFT:  collision = getObjectAt(new Vector2(position.x - i, position.y)); break;
                case Raycast.RIGHT: collision = getObjectAt(new Vector2(position.x + i, position.y)); break;
            }

            if(collision == null) continue;
            return collision;
        }

        return null;
    }
}
