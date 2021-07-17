package com.game.engine.engine.gui;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.core.Renderer;

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
        if(gc.getInput().getMouseX() >= posX && gc.getInput().getMouseX() <= posX + width) {
            if(gc.getInput().getMouseY() >= posY && gc.getInput().getMouseY() <= posY + height) {
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

        r.drawCurvedRect((int)posX, (int)posY, width, height, borderColour);
        r.drawFillRect(posX + 1, posY + 1, width - 2, height - 2, mainColour);
        r.drawText(text, (int)posX + r.getFont().getTextSpacing(), (int)posY, textColour);
    }

    public void setBounds(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
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
