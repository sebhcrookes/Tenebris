package com.game.engine.engine.core.rendering;

import com.game.engine.engine.core.GameEngine;
import com.game.engine.engine.gfx.Font;

public class TextRenderer {

    private Renderer r;
    private GameEngine engine;

    private Font font;

    public TextRenderer(GameEngine engine, Renderer r) {
        this.engine = engine;
        this.r = r;
        this.font = Font.STANDARD;
    }

    public void drawText(String text, int posX, int posY, int colour) {

        posX -= r.getCamX();
        posY -= r.getCamY();

        // Reserved colours
        int bgColour = 0xFFFF00FF;
        int charStart = 0xFF0000FF;
        int charEnd = 0xFFFFFF00;

        int offset = 0; // The offset we use while drawing

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i); // Get the unicode value for the character we are on

            for(int y = 0; y < font.getFontImage().getHeight(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    int pixelColour = font.getFontImage().getPixels()[x + font.getOffsets()[unicode] + y * font.getFontImage().getWidth()];
                    if(pixelColour != bgColour && pixelColour != charStart && pixelColour != charEnd) {
                        r.setPixel(x + posX + offset, y + posY, colour); // Set pixels on screen to the un-reserved font pixels
                    }
                }
            }
            offset += font.getWidths()[unicode]; // Add the width of the previously drawn character to our offset
        }
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
