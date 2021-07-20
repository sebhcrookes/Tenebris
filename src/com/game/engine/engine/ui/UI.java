package com.game.engine.engine.ui;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.core.Renderer;

import java.util.ArrayList;

public class UI {

    private GameEngine engine;

    private ArrayList<UIComponent> uiComponents = new ArrayList<>();

    public UI(GameEngine engine) {
        this.engine = engine;
    }

    public void update(EngineAPI api, float dt) {
        uiComponents.forEach(UIComponent -> UIComponent.update(api, dt));
    }

    public void render(EngineAPI api, Renderer r) {
        uiComponents.forEach(UIComponent -> UIComponent.render(api, r));
    }

    /**
     * Adds a UIComponent to the UI
     * @param uiComponent the component to add
     */
    public void addUIComponent(UIComponent uiComponent) {
        uiComponents.add(uiComponent);
    }

    public void setUIComponent(String tag, UIComponent uiComponent) {
        for(int i = 0; i < uiComponents.size(); i++) {
            if(uiComponents.get(i).getTag().equals(tag)) {
                uiComponents.set(i, uiComponent);
                return;
            }
        }
    }

    public UIComponent getUIComponent(String tag) {
        for(UIComponent uiComponent : uiComponents) {
            if(uiComponent.getTag().equals(tag)) {
                return uiComponent;
            }
        }
        return null;
    }

}
