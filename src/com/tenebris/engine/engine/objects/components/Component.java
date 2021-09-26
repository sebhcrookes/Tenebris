package com.tenebris.engine.engine.objects.components;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;

public abstract class Component {

    protected String tag;
    protected int priority = 0;

    public abstract void update(EngineAPI api, float dt);

    public abstract void render(EngineAPI api, Renderer r);

    public abstract void collision(GameObject other);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return this.tag;
    }
}
