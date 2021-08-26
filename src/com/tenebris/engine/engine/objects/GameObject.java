package com.tenebris.engine.engine.objects;

import com.tenebris.engine.engine.core.EngineAPI;
import com.tenebris.engine.engine.core.Renderer;
import com.tenebris.engine.engine.gfx.Image;
import com.tenebris.engine.engine.objects.components.Component;

import java.util.ArrayList;
import java.util.UUID;

public abstract class GameObject {

    protected String instanceID; // A unique identifier for each object

    protected String type;
    protected String name;
    protected Image image;
    protected int posX, posY;

    protected int width, height;

    protected ArrayList<Component> components;

    public GameObject(String type, String name, Image image, int posX, int posY, int width, int height) {
        this.type = type;
        this.name = name;
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.components = new ArrayList<>();

        this.instanceID = UUID.randomUUID().toString();
    }

    public abstract void update(EngineAPI api, float dt);

    public abstract void render(EngineAPI api, Renderer r);

    public abstract void collision(GameObject other);

    protected void renderImage(EngineAPI api, Renderer r) {
        r.drawImage(image, posX, posY);
    }

    public Component findComponent(String tag) {
        for(int i = 0; i < components.size(); i++) {
            if(components.get(i).getTag().equals(tag)) {
                return components.get(i);
            }
        }
        return null;
    }

    public void updateComponents(EngineAPI api, float dt) {
        for(int i = 0; i < components.size(); i++) {
            components.get(i).update(api, dt);
        }
    }

    public void renderComponents(EngineAPI api, Renderer r) {
        for(int i = 0; i < components.size(); i++) {
            components.get(i).render(api, r);
        }
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
}
