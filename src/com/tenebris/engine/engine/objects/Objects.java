package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.GameEngine;
import com.tenebris.engine.engine.core.Renderer;

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

    public ObjectHandle addObject(GameObject object) {
        objects.add(object);
        return new ObjectHandle(object.getUUID());
    }

    public GameObject getObject(ObjectHandle handle) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getUUID().equals(handle.getUUID())) {
                return objects.get(i);
            }
        }

        return null;
    }
}
