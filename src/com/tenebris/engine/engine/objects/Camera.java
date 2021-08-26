package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;

public class Camera {

    private int offX, offY;

    private String targetTag;
    private GameObject target = null;

    private boolean locked = false;

    public Camera() {}

    public Camera(String tag) {
        this.targetTag = tag;
    }

    public void init(EngineAPI api) {
        target = api.getObjects().getByName(targetTag);

        if(target != null) {

            int targetX = (int) ((target.getPosX() + target.getWidth() / 2) - api.getWidth() / 2);
            int targetY = (int) ((target.getPosY() + target.getHeight() / 2) - api.getHeight() / 2);

            offX = targetX;
            offY = targetY;
        } else {
            offX = 0;
            offY = 0;
        }
    }

    public void update(EngineAPI api, float dt) {

        if(!locked) {

            if (target == null) {
                target = api.getObjects().getByName(targetTag);
            }

            System.out.println(offX + " : " + offY);

            if (target == null) return;

//        if(target.getFacing() == Location.FACING_RIGHT) {
//            offX = offX + (api.getWidth() / 16) / 10;
//        }else if(target.getFacing() == Location.FACING_LEFT) {
//            offX = offX - (api.getWidth() / 16) / 10;
//        }

            int targetX = (int) ((target.getPosX() + target.getWidth() / 2) - api.getWidth() / 2);
            int targetY = (int) ((target.getPosY() + target.getHeight() / 2) - api.getHeight() / 2);

            offX -= (dt * (offX - targetX) * 4);
            offY -= (dt * (offY - targetY) * 7);


//        if(offX < 0) offX = 0;
//        if(offY < 0) offY = 0;
//        if(offX + api.getWidth() > gs.getLevelManager().getLevel().getLevelWidth() * gs.TS) offX = gs.getLevelManager().getLevel().getLevelWidth() * gs.TS - api.getWidth();
//        if(offY + api.getHeight() > gs.getLevelManager().getLevel().getLevelHeight() * gs.TS) offY = gs.getLevelManager().getLevel().getLevelHeight() * gs.TS - api.getHeight();

            offY = offY - ((api.getWidth() / 16) / 5);
        }
    }

    public void render(Renderer r) {
        r.setCamX(offX);
        r.setCamY(offY);
    }

    public void lock() {
        locked = true;
    }

    public void unlock() {
        locked = false;
    }

    public int getOffX() {
        return offX;
    }

    public void setOffX(int offX) {
        this.offX = offX;
    }

    public int getOffY() {
        return offY;
    }

    public void setOffY(int offY) {
        this.offY = offY;
    }

    public String getTargetTag() {
        return targetTag;
    }

    public void setTargetTag(String targetTag) {
        this.targetTag = targetTag;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }
}
