package com.game.engine.engine.core;

import com.game.engine.engine.core.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    private GameEngine engine;

    public Color backgroundColour;

    private double multiplier = 1.0;

    public Window(GameEngine engine) {
        this.engine = engine;
        frame = new JFrame(engine.getSettings().getTitle());
        backgroundColour = new Color(engine.getClearColour());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        canvas = new Canvas();
        Dimension s = new Dimension((int)(engine.getSettings().getWidth() * engine.getSettings().getScale()), (int)(engine.getSettings().getHeight() * engine.getSettings().getScale()));
        canvas.setPreferredSize(s);
        canvas.setEnabled(true);
        image = new BufferedImage(engine.getSettings().getWidth(), engine.getSettings().getHeight(), BufferedImage.TYPE_INT_RGB);

        frame.setBackground(backgroundColour);

        try {
            if (engine.getSettings().getIconPath() != null) {
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(engine.getSettings().getIconPath())));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.setLayout(new BorderLayout());
        try {
            if(engine.getSettings().getCursorPath() != null) {
                BufferedImage cursorImage;
                cursorImage = ImageIO.read(getClass().getResource(engine.getSettings().getCursorPath()));
                cursorImage.createGraphics();
                Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), engine.getSettings().getTitle() + " Default Cursor");
                frame.setCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
        frame.setState(Frame.NORMAL);
        frame.requestFocus();

        frame.setFocusable(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g =  bs.getDrawGraphics();
    }

    public void update(GameEngine gc) {

        multiplier = 1;

        Dimension s = new Dimension((int)((engine.getSettings().getWidth() * engine.getSettings().getScale()) * multiplier), (int)((engine.getSettings().getHeight() * engine.getSettings().getScale()) * multiplier));
        canvas.setSize(s);

        g.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight(),null);
        bs.show();
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}