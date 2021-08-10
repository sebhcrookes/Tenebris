package com.game.engine.engine.gfx;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class Image {

    public BufferedImage image;

    private int width, height;
    private int[] pixels;

    private boolean alpha = false;
    private int lightBlock = Light.NONE;

    private final String path;

    private int rotOffX = 0;
    private int rotOffY = 0;

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

    public void setRotation(int degree) {
        BufferedImage tempImg = rotate(image, degree);
        pixels = tempImg.getRGB(0, 0, width, height, null, 0, width);
    }

    private BufferedImage rotate(BufferedImage buffImage, double angle) {
        double radian = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(radian));
        double cos = Math.abs(Math.cos(radian));

        int width = buffImage.getWidth();
        int height = buffImage.getHeight();

        int nWidth = (int) Math.floor((double) width * cos + (double) height * sin);
        int nHeight = (int) Math.floor((double) height * cos + (double) width * sin);

        BufferedImage rotatedImage = new BufferedImage(
                nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = rotatedImage.createGraphics();

        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);

        graphics.rotate(radian, (double) (width / 2), (double) (height / 2));
        graphics.drawImage(buffImage, 0, 0, null);
        graphics.dispose();

        this.rotOffX = (nWidth / 2) - (width / 2);
        this.rotOffY = (nHeight / 2) - (height / 2);

        this.width = nWidth;
        this.height = nHeight;

        return rotatedImage;
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

    public int getRotOffX() {
        return rotOffX;
    }

    public void setRotOffX(int rotOffX) {
        this.rotOffX = rotOffX;
    }

    public int getRotOffY() {
        return rotOffY;
    }

    public void setRotOffY(int rotOffY) {
        this.rotOffY = rotOffY;
    }
}
