package com.game.engine.engine.gui;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.core.Renderer;

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
