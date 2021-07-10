package com.game.engine.engine.gui;

import com.game.engine.engine.GameEngine;
import com.game.engine.engine.Renderer;

import javax.swing.*;

public class GUIDialog extends GUIObject {

    public GUIDialog(String title, String text) {
        JOptionPane.showMessageDialog(new JFrame(), text, title,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update(GameEngine gc, float dt) {

    }

    @Override
    public void render(GameEngine gc, Renderer r) {

    }
}
