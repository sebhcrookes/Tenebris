package com.tenebris.engine.engine.objects.components.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.Component;
import com.tenebris.engine.engine.objects.components.Physics;

import java.util.ArrayList;

public class AABBComponent extends Component {

    private GameObject parent;

    private final ArrayList<String> collideWith = new ArrayList<>();

    private int centerX, centerY;
    private int halfWidth, halfHeight;

    private int lastCenterX, lastCenterY;

    private int lastPosY = 0;

    public AABBComponent(GameObject parent) {
        this.tag = "aabb";
        this.parent = parent;

        this.priority = Integer.MAX_VALUE;
    }

    @Override
    public void update(EngineAPI api, float dt) {

        // Grounded check
        parent.setGrounded(api.getObjects().existsBetween(parent.getPosX(), parent.getPosY() + parent.getHeight() + 1, parent.getPosX() + parent.getWidth(), parent.getPosY() + parent.getHeight() + 1));

        lastPosY = parent.getPosY();

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
        if (api.getSettings().isDebug()) {
            r.drawRect(centerX - halfWidth, centerY - halfHeight, halfWidth * 2, halfHeight * 2, 0xFF000000);
        }
    }

    @Override
    public void collision(GameObject other) {
        for (String s : collideWith) {
            if (other.getType().equalsIgnoreCase(s)) {
                AABBComponent thisC = this;
                AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

                if (Math.abs(thisC.getLastCenterX() - otherC.getLastCenterX()) < thisC.getHalfWidth() + otherC.getHalfWidth()) {

                    if (thisC.getCenterY() < otherC.getCenterY()) {
                        int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (otherC.getCenterY() - thisC.getCenterY());
                        parent.setPosY(parent.getPosY() - distance);
                        thisC.setCenterY(thisC.getCenterY() - distance);
                        PhysicsComponent p = ((PhysicsComponent) parent.findComponent("physics"));
                        if (p != null) p.resetFallVelocity();
                    }

                    if (thisC.getCenterY() > otherC.getCenterY()) {
                        int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (thisC.getCenterY() - otherC.getCenterY());
                        parent.setPosY(parent.getPosY() + distance);
                        thisC.setCenterY(thisC.getCenterY() + distance);
                        PhysicsComponent p = ((PhysicsComponent) parent.findComponent("physics"));
                        if (p != null) p.resetFallVelocity();
                    }

                } else {
                    if (thisC.getCenterX() < otherC.getCenterX()) {
                        int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (otherC.getCenterX() - thisC.getCenterX());
                        parent.setPosX(parent.getPosX() - distance);
                        thisC.setCenterX(thisC.getCenterX() - distance);
                    } else if (thisC.getCenterX() > otherC.getCenterX()) {
                        int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (thisC.getCenterX() - otherC.getCenterX());
                        parent.setPosX(parent.getPosX() + distance);
                        thisC.setCenterX(thisC.getCenterX() + distance);
                    }
                }
            }
        }
    }

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

    public ArrayList<String> getCollideWith() {
        return collideWith;
    }

    public void addCollision(String name) {
        collideWith.add(name);
    }
}
