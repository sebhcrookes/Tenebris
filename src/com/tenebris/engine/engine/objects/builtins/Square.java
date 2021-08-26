package com.tenebris.engine.engine.objects.builtins;

import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.components.builtins.AABBComponent;
import com.tenebris.engine.engine.objects.components.builtins.PhysicsComponent;

public class Square extends GameObject {

    public Square(String name, int posX, int posY, int width, int height) {
        super("square", name, null, posX, posY, width, height);

        components.add(new AABBComponent(this));
        components.add(new PhysicsComponent(this));
    }

    @Override
    public void update(EngineAPI api, float dt) {

    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawRect(posX, posY, width, height, 0xFFFFFFFF);
    }

    @Override
    public void collision(GameObject other) {
        if(other.getType().equalsIgnoreCase("square")) {
            AABBComponent thisC = (AABBComponent) this.findComponent("aabb");
            AABBComponent otherC = (AABBComponent) other.findComponent("aabb");

            if(Math.abs(thisC.getLastCenterX() - otherC.getLastCenterX()) < thisC.getHalfWidth() + otherC.getHalfWidth()) {
                if (thisC.getCenterY() < otherC.getCenterY()) {
                    int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (otherC.getCenterY() - thisC.getCenterY());
                    posY -= distance;
                    thisC.setCenterY(thisC.getCenterY() - distance);
                }
                if (thisC.getCenterY() > otherC.getCenterY()) {
                    int distance = thisC.getHalfHeight() + otherC.getHalfHeight() - (thisC.getCenterY() - otherC.getCenterY());
                    posY += distance;
                    thisC.setCenterY(thisC.getCenterY() + distance);
                }
            }else{
                if (thisC.getCenterX() < otherC.getCenterX()) {
                    int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (otherC.getCenterX() - thisC.getCenterX());
                    posX -= distance;
                    thisC.setCenterX(thisC.getCenterX() - distance);
                }
                if (thisC.getCenterX() > otherC.getCenterX()) {
                    int distance = thisC.getHalfWidth() + otherC.getHalfWidth() - (thisC.getCenterX() - otherC.getCenterX());
                    posX += distance;
                    thisC.setCenterX(thisC.getCenterX() + distance);
                }
            }
        }
    }
}
