package com.tenebris.engine.engine.core;

import java.awt.event.*;
import java.util.ArrayList;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private final int NUM_KEYS = 256;
    private final int NUM_BUTTONS = 5;

    private final GameEngine gc;

    private final boolean[] keys = new boolean[NUM_KEYS];
    private final boolean[] keysLast = new boolean[NUM_KEYS];
    private final boolean[] buttons = new boolean[NUM_BUTTONS];
    private final boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY;
    private int scroll;

    private boolean typing;
    private ArrayList<String> typed;

    public Input(GameEngine gc) {
        this.gc = gc;
        mouseX = 0;
        mouseY = 0;
        scroll = 0;

        typing = false;
        typed = new ArrayList<>();

        gc.getWindow().getCanvas().addKeyListener(this);
        gc.getWindow().getCanvas().addMouseMotionListener(this);
        gc.getWindow().getCanvas().addMouseListener(this);
        gc.getWindow().getCanvas().addMouseWheelListener(this);
    }

    public void update() {
        scroll = 0;
        for (int i = 0; i < NUM_KEYS; i++) {
            keysLast[i] = keys[i];
        }
        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttonsLast[i] = buttons[i];
        }
    }

    //Keys
    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }

    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }

    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }

    //Buttons
    public boolean isButton(int button) {
        return buttons[button];
    }

    public boolean isButtonUp(int button) {
        return !buttons[button] && buttonsLast[button];
    }

    public boolean isButtonDown(int button) {
        return buttons[button] && !buttonsLast[button];
    }

    public void startTyping() {
        this.typing = true;
        typed.add("");
    }

    public ArrayList<String> endTyping() {
        this.typing = false;
        ArrayList<String> tmp = typed;
        typed.clear();
        return tmp;
    }

    public ArrayList<String> getTyped() {
        return typed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(typing) {
            switch(e.getKeyChar()) {
                case 0x08: // Backspace
                    try {
                        typed.set(typed.size() - 1, typed.get(typed.size() - 1).substring(0, typed.get(typed.size() - 1).length() - 1));
                    } catch(StringIndexOutOfBoundsException ignored) {
                        if(typed.size() != 1) typed.remove(typed.size() - 1);
                    }
                    break;
                case 0x0A: // Newline
                    typed.add(""); break;
                default:
                    if(e.getKeyChar() > 0x1F) // If it's a valid, typeable character
                        typed.set(typed.size() - 1, typed.get(typed.size() - 1) + e.getKeyChar());
                    break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX() / gc.getSettings().getScale());
        mouseY = (int) (e.getY() / gc.getSettings().getScale());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / gc.getSettings().getScale());
        mouseY = (int) (e.getY() / gc.getSettings().getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }


    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getScroll() {
        return scroll;
    }
}