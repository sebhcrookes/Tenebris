package com.game.engine.engine.gui;

import com.game.engine.engine.GameContainer;
import com.game.engine.engine.Renderer;
import com.game.engine.engine.position.Vector2;
import com.game.engine.game.GameManager;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class GUIButton extends GUIObject {

    private int width, height;
    private String text = "";

    private int borderColour = 0xFFFFFFFF;
    private int mainColour = 0xFFBABABA;
    private int textColour = 0xFFFFFFFF;

    private Consumer clickConsumer;

    private boolean packRequest = false;

    public GUIButton() {}

    @Override
    public void update(GameContainer gc, float dt) {
        if(gc.getInput().getMouseX() >= position.getPosX() && gc.getInput().getMouseX() <= position.getPosX() + width) {
            if(gc.getInput().getMouseY() >= position.getPosY() && gc.getInput().getMouseY() <= position.getPosY() + height) {
                // Mouse is hovered over button
                if(gc.getInput().isButtonDown(MouseEvent.BUTTON1)) {
                    try{
                        clickConsumer.accept(null);
                    } catch(Exception ignored) {}
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if(packRequest) {
            this.width = r.textLength(this.text) + 1;
            this.height = 10;
            packRequest = false;
        }

        r.drawCurvedRect(position.getPosX(), position.getPosY(), width, height, borderColour);
        r.drawFillRect(position.getPosX() + 1, position.getPosY() + 1, width - 2, height - 2, mainColour);
        r.drawText(text, position.getPosX() + 1, position.getPosY(), textColour);
    }

    public void setBounds(Vector2 position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public void pack() {
        packRequest = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setClickConsumer(Consumer clickConsumer) {
        this.clickConsumer = clickConsumer;
    }

    public int getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(int borderColour) {
        this.borderColour = borderColour;
    }

    public int getMainColour() {
        return mainColour;
    }

    public void setMainColour(int mainColour) {
        this.mainColour = mainColour;
    }

    public int getTextColour() {
        return textColour;
    }

    public void setTextColour(int textColour) {
        this.textColour = textColour;
    }
}
