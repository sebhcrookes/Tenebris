package com.game.engine.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Image {
    public BufferedImage image;
    private int width, height;
    private int[] pixels;
    private boolean alpha = false;
    private int lightBlock = Light.NONE;
    private final String path;

    public Image(String path) {
        this.path = path;

        BufferedImage image = null;
        try {
            image = ImageIO.read(Image.class.getResourceAsStream(path));
            image.getScaledInstance(image.getWidth(), image.getHeight(), java.awt.Image.SCALE_SMOOTH);

        } catch (Exception e) {
            try {
                Graphics2D graphics = image.createGraphics();
                graphics.setPaint(new Color(255, 255, 255));
                graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            } catch (Exception ignored) {
            }
        }

        try {
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
        } catch (Exception ignored) {
        }

        this.image = image;
    }


    public int[] getPixels() {
        return pixels;
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getP(int position) {
        return pixels[position];
    }

    public void setP(int position, int value) {
        this.pixels[position] = value;
    }

    public void setAlphaVal(int alpha) {
        for (int i = 0; i < getWidth() * getHeight(); i++) {
            int alphaVal;
            int currentAlpha = ((getP(i) >> 24) & 0xff);
            if (currentAlpha != 255) {
                continue;
            }
            alphaVal = alpha;

            int pixel = getP(i);
            Color rgba = new Color((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff, alphaVal);
            setP(i, rgba.getRGB());
        }
    }

    public int getLightBlock() {
        return lightBlock;
    }

    public void setLightBlock(int lightBlock) {
        this.lightBlock = lightBlock;
    }

    public boolean isAlpha() {
        return alpha;
    }

    public void setAlpha(boolean alpha) {
        this.alpha = alpha;
    }

    public String getPath() {
        return path;
    }
}
