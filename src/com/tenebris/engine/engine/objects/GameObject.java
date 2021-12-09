package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.components.Component;
import com.tenebris.engine.engine.util.Log;
import com.tenebris.engine.engine.util.Vector2;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameObject {

    protected final String tag;

    protected boolean isVisible = true;
    protected Vector2 position;
    protected Vector2 size;

    protected final UUID uuid;

    private ArrayList<Component> components;

    public GameObject(String tag, Vector2 position, Vector2 size) {
        this.tag = tag;
        this.position = position;
        this.size = size;

        this.components = new ArrayList<>();

        this.uuid = UUID.randomUUID(); // ("A UUID is 128 bits long, and can guarantee uniqueness across space and time" - https://www.ietf.org/rfc/rfc4122.txt)
    }

    public abstract void update(EngineAPI api, float dt);
    public abstract void render(EngineAPI api, Renderer r);

    public Component getComponent(String tag) {
        for(int i = 0; i < components.size(); i++) {
            if(components.get(i).getTag().equals(tag)) {
                return components.get(i);
            }
        }

        return null;
    }

    public void addComponent(Component component) {
        // Check if component already exists
        for(int i = 0; i < components.size(); i++) {
            if(components.get(i).getTag().equals(component.getTag())) {
                Log.error("Failed to add component '" + component.getTag() + "' to object '" + tag + "': a component of the same type already exists");
                return;
            }
        }

        components.add(component);
    }

    public void updateComponents(EngineAPI api, float dt) {
        for(int i = 0; i < components.size(); i++) {
            components.get(i).update(api, dt);
        }
    }

    public void renderComponents(EngineAPI api, Renderer r) {
        for(int i = 0; i < components.size(); i++) {
            components.get(i).render(api, r);
        }
    }

    public String getTag() {
        return tag;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public UUID getUUID() {
        return uuid;
    }
}
