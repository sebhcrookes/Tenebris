package com.game.engine.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

public class Image implements Serializable {
    private int w,h;
    private int[] p;
    private boolean alpha = false;

    private String path;

    public String tag = "";

    public transient BufferedImage image;

    public Image(String path) {
        this.path = path;

        File pathFile = new File(path);

        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
            image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);

        } catch (Exception e) {
            try {
                Graphics2D graphics = image.createGraphics();
                graphics.setPaint ( new Color ( 255, 255, 255) );
                graphics.fillRect ( 0, 0, image.getWidth(), image.getHeight() );

            }catch(Exception exception) {}
        }

        try {
            w = image.getWidth();
            h = image.getHeight();
            p = image.getRGB(0, 0, w, h, null, 0, w);
        }catch(Exception e) {}

        this.image = image;
    }



    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int[] getP() {
        return p;
    }

    public int getSingleP(int i) {
        return p[i];
    }

    public void setP(int i, int p) {
        this.p[i] = p;
    }

    public void printP() {
        for(int i = 0; i < p.length; i++) {
            System.out.println(p[i]);
        }
    }

    public Image getAlphaVal(int a) {
        for (int i = 0; i < getW() * getH(); i++) {
            int alphaVal;
            int currentAlpha = ((getSingleP(i) >> 24) & 0xff);
            if(currentAlpha != 255) { continue; }
            alphaVal = a;

            int pixel = getSingleP(i);
            Color rgba = new Color((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff, alphaVal);
            setP(i, rgba.getRGB());
        }

        return this;

    }

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public String getPath() {
        return this.path;
    }

    public void setAlpha(int value) {

    }
}
