package com.game.engine.engine.gui;

import com.game.engine.engine.GameEngine;
import com.game.engine.engine.Renderer;
import com.game.engine.engine.position.Vector2;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class GUIButton extends GUIObject {

    public int width, height;
    private String text = "";

    private int borderColour = 0xFFFFFFFF;
    private int mainColour = 0xFFBABABA;
    private int textColour = 0xFFFFFFFF;

    private Consumer clickConsumer;

    private boolean packRequest = false;

    public GUIButton() {}

    @Override
    public void update(GameEngine gc, float dt) {
        if(gc.getInput().getMouseX() >= position.getX() && gc.getInput().getMouseX() <= position.getX() + width) {
            if(gc.getInput().getMouseY() >= position.getY() && gc.getInput().getMouseY() <= position.getY() + height) {
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
    public void render(GameEngine gc, Renderer r) {
        if(packRequest) {
            this.width = r.textLength(this.text) + r.getFont().getTextSpacing();
            this.height = 10;
            packRequest = false;
        }

        r.drawCurvedRect(position.getX(), position.getY(), width, height, borderColour);
        r.drawFillRect(new Vector2(position.X + 1, position.Y + 1), width - 2, height - 2, mainColour);
        r.drawText(text, position.getX() + r.getFont().getTextSpacing(), position.getY(), textColour);
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
