package com.game.engine.engine.gfx;

public class Font {

    public static final Font STANDARD = new Font("/fonts/standard.png", 1);

    private Image fontImage;
    private int[] offsets;
    private int[] widths;

    private final int textSpacing;

    public Font(String path, int textSpacing) {
        fontImage = new Image(path);
        offsets = new int[256];
        widths = new int[256];

        this.textSpacing = textSpacing;

        int unicode = 0;

        for (int i = 0; i < fontImage.getWidth(); i++) {
            if (fontImage.getPixels()[i] == 0xff0000ff) {
                offsets[unicode] = i + textSpacing;
            }

            if (fontImage.getPixels()[i] == 0xffffff00) {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    /**
     * Calculates the length of a string, in pixels
     *
     * @param text Text to calculate
     * @return Length of text, in pixels
     */
    public int getTextLength(String text) {
        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);
            offset += getWidths()[unicode];
        }
        return offset;
    }

    /**
     * Calculates the height of the font, in pixels
     *
     * @return Height of font, in pixels
     */
    public int getFontHeight() {
        return fontImage.getHeight() - 1;
    }

    public int getTextSpacing() {
        return textSpacing;
    }

    public Image getFontImage() {
        return fontImage;
    }

    public void setFontImage(Image fontImage) {
        this.fontImage = fontImage;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public void setOffsets(int[] offsets) {
        this.offsets = offsets;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] widths) {
        this.widths = widths;
    }

    public int getHeight() {
        return fontImage.getHeight();
    }

}
