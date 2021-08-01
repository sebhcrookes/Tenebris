package com.game.engine.game;

import javax.swing.*;

public class Launcher {

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        GameManager gm = new GameManager();
        try {
            gm.start();
        } catch (Exception e) {
            System.out.println("There was an error during runtime. Exception: " + e.toString());
            e.printStackTrace();
            String message = e.toString();
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(new JFrame(), message, "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

}
