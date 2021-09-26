package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.gfx.Image;
import com.tenebris.engine.engine.objects.components.Component;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public abstract class GameObject {

    protected boolean dead;

    protected String instanceID; // A unique identifier for each object
    protected long timeOfInstantiation;

    protected String type;
    protected String name;
    protected Image image;
    protected int posX, posY;

    protected int width, height;

    protected boolean grounded;

    protected ArrayList<Component> components;

    public GameObject(String type, String name, Image image, int posX, int posY, int width, int height) {
        this.type = type;
        this.name = name;
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.grounded = false;

        this.components = new ArrayList<>();

        this.timeOfInstantiation = System.nanoTime();

        this.instanceID = UUID.randomUUID().toString(); // ("A UUID is 128 bits long, and can guarantee uniqueness across space and time" - https://www.ietf.org/rfc/rfc4122.txt)
    }

    public abstract void update(EngineAPI api, float dt);

    public abstract void render(EngineAPI api, Renderer r);

    public abstract void collision(GameObject other);

    protected void renderImage(EngineAPI api, Renderer r) {
        r.drawImage(image, posX, posY);
    }

    public Component findComponent(String tag) {
        for (Component component : components) {
            if (component.getTag().equals(tag)) return component;
        }
        return null;
    }

    public void addComponent(Component component) {

        int index = 0;

        if (components.size() != 0) index = components.size();

        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getPriority() > component.getPriority()) {
                index = i;
                break;
            }
        }

        components.add(index, component);
    }

    public void updateComponents(EngineAPI api, float dt) {
        for (Component component : components)
            component.update(api, dt);
    }

    public void renderComponents(EngineAPI api, Renderer r) {
        for (Component component : components)
            component.render(api, r);

    }

    public void collideComponents(GameObject other) {
        for (Component component : components)
            component.collision(other);
    }

    public void removeComponent(String tag) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).getTag().equals(tag)) {
                components.remove(i);
                return;
            }
        }
    }

    public void listComponents() {
        System.out.println(name + " = {");
        for (int i = 0; i < components.size(); i++) {
            System.out.print("    C" + i + " = ('" + components.get(i).getTag() + "', 0x" + Integer.toHexString(components.get(i).getPriority()).toUpperCase(Locale.ROOT) + ")");
            if (i + 1 != components.size()) System.out.print(",");
            System.out.print("\n");
        }
        System.out.println("}\n");
    }

    @Override
    public String toString() {
        return this.instanceID;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public String getInstanceID() {
        return instanceID;
    }

    public void setInstanceID(String instanceID) {
        this.instanceID = instanceID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }
}
