package com.tenebris.engine.engine.core;

import com.tenebris.engine.engine.gfx.Colour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {

    public Color backgroundColour;

    private final JFrame frame;
    private BufferedImage image;
    private final Canvas canvas;
    private final BufferStrategy bs;
    private final Graphics g;
    private final GameEngine engine;

    public Window(GameEngine engine) {
        this.engine = engine;

        int colour = engine.getClearColour();

        backgroundColour = new Color(Colour.getRed(colour), Colour.getGreen(colour), Colour.getBlue(colour));
        image = new BufferedImage(engine.getSettings().getWidth(), engine.getSettings().getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();

        Dimension s = new Dimension((int) (engine.getSettings().getWidth() * engine.getSettings().getScale()), (int) (engine.getSettings().getHeight() * engine.getSettings().getScale()));

        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);
        canvas.setFocusable(true);

        frame = new JFrame(engine.getSettings().getTitle());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backgroundColour);

        try {
            frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/misc/icon.png")));
        } catch (Exception ignored) {
        }

        frame.setLayout(new BorderLayout());

        try {
            BufferedImage cursorImage;
            cursorImage = ImageIO.read(getClass().getResource("/assets/misc/cursor.png"));
            cursorImage.createGraphics();
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "game.engine Default Cursor");
            frame.setCursor(cursor);
        } catch (Exception ignored) {
        }

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(false);
        frame.setState(Frame.NORMAL);
        frame.requestFocus();

        canvas.createBufferStrategy(2);
        canvas.setBackground(backgroundColour);
        canvas.setEnabled(true);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();
    }

    public void update() {
        g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
        if(engine.getSettings().isShowFPS()) frame.setTitle(engine.getSettings().getTitle() + " - FPS: " + engine.getFps());

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

    public void setScale(int scale) {
        Dimension s = new Dimension((int) (engine.getSettings().getWidth() * engine.getSettings().getScale()), (int) (engine.getSettings().getHeight() * engine.getSettings().getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocus();
    }


    public void setVisible(boolean visible) {
        frame.setVisible(true);
    }

}
