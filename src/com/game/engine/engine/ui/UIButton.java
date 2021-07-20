package com.game.engine.engine.ui;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;

import java.awt.event.MouseEvent;

public class UIButton extends UIComponent {

    private boolean isEnabled = false;
    private int backgroundColour = 0xFF43464B;
    private int textColour = 0xFFFFFFFF;
    private int borderColour = 0xFFFFFFFF;

    private String text = "Button";
    private int textAlignment = Alignment.CENTER;

    private boolean isLeft = false;
    private boolean isLeftDown = false;

    private boolean isRight = false;
    private boolean isRightDown = false;

    private boolean isHovered = false;

    public UIButton() { super(); }

    public UIButton(boolean isEnabled) {
        super();
        this.isEnabled = isEnabled;
    }

    public UIButton(boolean isEnabled, int backgroundColour) {
        super();
        this.isEnabled = isEnabled;
        this.backgroundColour = backgroundColour;
    }

    public UIButton(int backgroundColour) {
        super();
        this.backgroundColour = backgroundColour;
    }

    @Override
    public void update(EngineAPI api, float dt) {
        if(api.getInput().getMouseX() > posX && api.getInput().getMouseX() < posX + size.getWidth() && api.getInput().getMouseY() > posY && api.getInput().getMouseY() < posY + size.getHeight() && isEnabled) {
            isHovered = true;
            if(api.getInput().isButton(MouseEvent.BUTTON1)) { // Left mouse button
                if(!isLeft) {
                    isLeftDown = true;
                    isLeft = true;
                }else{
                    isLeftDown = false;
                }
            }else{
                isLeft = false;
                isLeftDown = false;
            }
            if(api.getInput().isButton(MouseEvent.BUTTON3)) { // Right mouse button
                if(!isRight) {
                    isRightDown = true;
                    isRight = true;
                }else{
                    isRightDown = false;
                }
            }else{
                isRight = false;
                isRightDown = false;
            }
        }else{
            isHovered = false;
            isLeft = false;
            isRight = false;
            isLeftDown = false;
            isRightDown = false;
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawFillRect(posX, posY, size.getWidth(), size.getHeight(), backgroundColour);
        r.drawRect((int)posX, (int)posY, size.getWidth(), size.getHeight(), borderColour);
        switch(textAlignment) {
            case Alignment.CENTER:
                r.drawText(text, (int)posX + ((size.getWidth() / 2) - (r.getFont().getTextLength(text) / 2)), (int)posY + ((size.getHeight() / 2) - r.getFont().getFontHeight() / 2), textColour);
                break;
            case Alignment.LEFT:
                r.drawText(text, (int)posX, (int)posY + ((size.getHeight() / 2) - r.getFont().getFontHeight() / 2), textColour);
                break;
            case Alignment.RIGHT:
                r.drawText(text, (int)posX + (size.getWidth() - r.getFont().getTextLength(text)), (int)posY + ((size.getHeight() / 2) - r.getFont().getFontHeight() / 2), textColour);
                break;
            case Alignment.TOP:
                r.drawText(text, (int)posX + ((size.getWidth() / 2) - (r.getFont().getTextLength(text) / 2)), (int)posY, textColour);
                break;
            case Alignment.BOTTOM:
                r.drawText(text, (int)posX + ((size.getWidth() / 2) - (r.getFont().getTextLength(text) / 2)), (int)posY + (size.getHeight() - r.getFont().getFontHeight()), textColour);
                break;
            case Alignment.TOP_LEFT:
                r.drawText(text, (int)posX, (int)posY, textColour);
                break;
            case Alignment.TOP_RIGHT:
                r.drawText(text, (int)posX + (size.getWidth() - r.getFont().getTextLength(text)), (int)posY, textColour);
                break;
            case Alignment.BOTTOM_LEFT:
                r.drawText(text, (int)posX, (int)posY + (size.getHeight() - r.getFont().getFontHeight()), textColour);
                break;
            case Alignment.BOTTOM_RIGHT:
                r.drawText(text, (int)posX + (size.getWidth() - r.getFont().getTextLength(text)), (int)posY + (size.getHeight() - r.getFont().getFontHeight()), textColour);
                break;
        }
        if(!isEnabled) {
            r.drawFillRect(posX, posY, size.getWidth(), size.getHeight(), 0x3F000000);
        }else{
            if(isHovered) {
                if(!isLeft) {
                    r.drawFillRect(posX, posY, size.getWidth(), size.getHeight(), 0x2FFFFFFF);
                }else{
                    r.drawFillRect(posX, posY, size.getWidth(), size.getHeight(), 0x2FF0F0F0);
                }
            }
        }
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(int backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public int getTextColour() {
        return textColour;
    }

    public void setTextColour(int textColour) {
        this.textColour = textColour;
    }

    public int getBorderColour() {
        return borderColour;
    }

    public void setBorderColour(int borderColour) {
        this.borderColour = borderColour;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextAlignment() {
        return textAlignment;
    }

    public void setTextAlignment(int textAlignment) {
        this.textAlignment = textAlignment;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public boolean isLeftDown() {
        return isLeftDown;
    }

    public boolean isRight() {
        return isRight;
    }

    public boolean isRightDown() {
        return isRightDown;
    }
}
