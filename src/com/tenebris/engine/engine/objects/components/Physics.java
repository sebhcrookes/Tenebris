package com.tenebris.engine.engine.objects.components;

import com.tenebris.engine.engine.objects.components.builtins.AABBComponent;

import java.util.ArrayList;

public class Physics {

    private static final ArrayList<AABBComponent> aabbList = new ArrayList<>();

    public static void addAABBComponent(AABBComponent aabb) {
        aabbList.add(aabb);
    }

    public static void update() {
        for (int i = 0; i < aabbList.size(); i++) {
            for (int j = i + 1; j < aabbList.size(); j++) {
                AABBComponent c0 = aabbList.get(i);
                AABBComponent c1 = aabbList.get(j);

                if (Math.abs(c0.getCenterX() - c1.getCenterX()) < c0.getHalfWidth() + c1.getHalfWidth()
                        && Math.abs(c0.getCenterY() - c1.getCenterY()) < c0.getHalfHeight() + c1.getHalfHeight()) {
                    if (!c0.getParent().getInstanceID().equals(c1.getParent().getInstanceID())) {
                        c0.getParent().collision(c1.getParent());
                        c0.getParent().collideComponents(c1.getParent());

                        c1.getParent().collision(c0.getParent());
                        c1.getParent().collideComponents(c0.getParent());
                    }
                }
            }
        }

        aabbList.clear();
    }

    public static void clear() {
        aabbList.clear();
    }
}
