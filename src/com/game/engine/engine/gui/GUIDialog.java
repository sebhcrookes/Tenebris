package com.game.engine.engine.gui;

import com.game.engine.engine.GameContainer;
import com.game.engine.engine.Renderer;
import com.game.engine.game.GameManager;

import javax.swing.*;

public class GUIDialog extends GUIObject {

    public GUIDialog(String title, String text) {
        JOptionPane.showMessageDialog(new JFrame(), text, title,
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void update(GameContainer gc, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer r) {

    }
}
