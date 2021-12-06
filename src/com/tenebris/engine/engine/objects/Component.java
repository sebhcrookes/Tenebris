package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;

public abstract class Component {

    protected GameObject parent;
    protected final String tag;

    public Component(String tag, GameObject parent) {
        this.tag = tag;
        this.parent = parent;
    }

    public abstract void update(EngineAPI api, float dt);
    public abstract void render(EngineAPI api, Renderer r);

    public String getTag() {
        return tag;
    }
}
