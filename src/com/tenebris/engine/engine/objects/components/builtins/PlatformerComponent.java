package com.tenebris.engine.engine.objects.components.builtins;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.objects.GameObject;
import com.tenebris.engine.engine.objects.components.Component;

import java.util.ArrayList;

public class PlatformerComponent extends Component {

    private GameObject parent;

    private ArrayList<Integer> jumpBinds = new ArrayList<>();
    private ArrayList<Integer> rightBinds = new ArrayList<>();
    private ArrayList<Integer> leftBinds = new ArrayList<>();

    private int speed = 1;
    private double jumpPower = 2.8;

    public PlatformerComponent(GameObject parent) {
        this.parent = parent;
        this.tag = "platformer";
        this.priority = 0;
    }

    @Override
    public void update(EngineAPI api, float dt) {
        for (int key : rightBinds) {
            if (api.getInput().isKey(key)) parent.setPosX((int) (parent.getPosX() + speed));
        }

        for (int key : leftBinds) {
            if (api.getInput().isKey(key)) parent.setPosX((int) (parent.getPosX() - speed));
        }

        PhysicsComponent physicsComponent = (PhysicsComponent) parent.findComponent("physics");

        if (physicsComponent != null) {
            for (int key : jumpBinds) {
                if (api.getInput().isKey(key) && parent.isGrounded()) {
                    physicsComponent.setFallVelocity(-jumpPower);
                    parent.setGrounded(false);
                    break;
                }
            }
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {

    }

    @Override
    public void collision(GameObject other) {

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setJumpPower(int jumpPower) {
        this.jumpPower = jumpPower;
    }

    public double getJumpPower() {
        return jumpPower;
    }

    public void addJumpBind(int key) {
        jumpBinds.add(key);
    }

    public void removeJumpBind(int key) {
        jumpBinds.remove(key);
    }

    public void addRightBind(int key) {
        rightBinds.add(key);
    }

    public void removeRightBind(int key) {
        rightBinds.remove(key);
    }

    public void addLeftBind(int key) {
        leftBinds.add(key);
    }

    public void removeLeftBind(int key) {
        leftBinds.remove(key);
    }
}
