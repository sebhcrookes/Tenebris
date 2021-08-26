package com.tenebris.engine.engine.objects.components.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.Component;
import com.tenebris.engine.engine.objects.components.Physics;

public class AABBComponent extends Component {

    private GameObject parent;

    private int centerX, centerY;
    private int halfWidth, halfHeight;

    private int lastCenterX, lastCenterY;

    public AABBComponent(GameObject parent) {
        this.tag = "aabb";
        this.parent = parent;
    }

    @Override
    public void update(EngineAPI api, float dt) {

        lastCenterX = centerX;
        lastCenterY = centerY;

        centerX = parent.getPosX() + (parent.getWidth() / 2);
        centerY = parent.getPosY() + (parent.getHeight() / 2);

        halfWidth = parent.getWidth() / 2;
        halfHeight = parent.getHeight() / 2;

        Physics.addAABBComponent(this);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        if(api.getSettings().isDebug()) {
            r.drawRect(centerX - halfWidth, centerY - halfHeight, halfWidth * 2, halfHeight * 2, 0xFF000000);
        }
    }

    @Override
    public void collision(GameObject other) {}

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getHalfWidth() {
        return halfWidth;
    }

    public void setHalfWidth(int halfWidth) {
        this.halfWidth = halfWidth;
    }

    public int getHalfHeight() {
        return halfHeight;
    }

    public void setHalfHeight(int halfHeight) {
        this.halfHeight = halfHeight;
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public int getLastCenterX() {
        return lastCenterX;
    }

    public void setLastCenterX(int lastCenterX) {
        this.lastCenterX = lastCenterX;
    }

    public int getLastCenterY() {
        return lastCenterY;
    }

    public void setLastCenterY(int lastCenterY) {
        this.lastCenterY = lastCenterY;
    }
}
