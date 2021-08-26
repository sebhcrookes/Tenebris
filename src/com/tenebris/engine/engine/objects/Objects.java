package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.GameEngine;
import com.tenebris.engine.engine.core.Renderer;

import java.util.ArrayList;

public class Objects {

    private GameEngine engine;

    private ArrayList<GameObject> objects = new ArrayList<>();

    public Objects(GameEngine engine) {
        this.engine = engine;
    }

    public void update(EngineAPI api, float dt) {
        objects.forEach(gameObject -> {
            gameObject.update(api, dt);
            gameObject.updateComponents(api, dt);
        });
    }

    public void render(EngineAPI api, Renderer r) {
        objects.forEach(gameObject -> {
            gameObject.render(api, r);
            gameObject.renderComponents(api, r);
        });
    }

    public void add(GameObject object) {
        objects.add(object);
    }

    public GameObject get(String tag) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getType().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public GameObject getByName(String name) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getName().equals(name)) {
                return objects.get(i);
            }
        }
        return null;
    }
}
