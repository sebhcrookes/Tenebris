package com.game.engine.engine;

import com.game.engine.game.GameManager;

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

    private GameContainer gc;

    public Color backgroundColour;

    public Window(GameContainer gc) {
        this.gc = gc;

        backgroundColour = new Color(gc.clearColour);

        image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(backgroundColour);

        try {
            if (EngineSettings.getIconPath() != null) {
                frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(EngineSettings.getIconPath())));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        frame.setLayout(new BorderLayout());
        try {
            if(EngineSettings.getCursorPath() != null) {
                BufferedImage cursorImage;
                cursorImage = ImageIO.read(getClass().getResource(EngineSettings.getCursorPath()));
                cursorImage.createGraphics();
                Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0,0), EngineSettings.getTitle() + " Default Cursor");
                frame.setCursor(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setState(Frame.NORMAL);
        frame.requestFocus();

        canvas.createBufferStrategy(2);
        canvas.setBackground(backgroundColour);
        canvas.setEnabled(true);
        bs = canvas.getBufferStrategy();
        g =  bs.getDrawGraphics();
    }

    public void update() {
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

    public void setScale(int scale) {
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
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

}
