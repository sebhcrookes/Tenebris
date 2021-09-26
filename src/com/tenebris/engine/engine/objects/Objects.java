package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.GameEngine;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.components.Physics;

import java.util.ArrayList;

public class Objects {

    private final GameEngine engine;

    private final ArrayList<GameObject> objects = new ArrayList<>();

    private ArrayList<GameObject> destroy = new ArrayList<>();

    public Objects(GameEngine engine) {
        this.engine = engine;
    }

    public void update(EngineAPI api, float dt) {

        for (GameObject gameObject : destroy) objects.remove(gameObject);
        destroy.clear();

        objects.forEach(gameObject -> {
            gameObject.update(api, dt);
            gameObject.updateComponents(api, dt);

            if (gameObject.isDead()) destroy.add(gameObject);
        });

        Physics.update();

        for (GameObject gameObject : destroy) objects.remove(gameObject);
        destroy.clear();
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

    public GameObject get(String name) {
        for (GameObject object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }

    public void remove(String name) {
        for (GameObject object : objects) {
            if(object.getName().equals(name)) {
                destroy.add(object);
                return;
            }
        }
    }

    public boolean existsAt(int posX, int posY) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getPosX() < posX && objects.get(i).getPosX() + objects.get(i).getWidth() > posX) {
                if(objects.get(i).getPosY() < posY && objects.get(i).getPosY() + objects.get(i).getHeight() > posY) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existsBetween(int x1, int y1, int x2, int y2) {
        for(int y = 0; y < y2 - y1 + 1; y++) {
            for(int x = 0; x < x2 - x1; x++) {
                if(existsAt(x1 + x, y1 + y)) {
                    return true;
                }
            }
        }

        return false;
    }

    public GameObject getByName(String name) {
        for (GameObject object : objects) {
            if (object.getName().equals(name)) {
                return object;
            }
        }
        return null;
    }
}
