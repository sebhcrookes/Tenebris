package com.game.engine.engine.util.terminal;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Terminal {

    private boolean isEnabled = false;
    private boolean isLogging = true;

    private ArrayList<String> terminalContents = new ArrayList<>();
    private ArrayList<String> colouring = new ArrayList<>();

    private final HashMap<String, Integer> terminalColours = new HashMap<>();

    public Terminal() {
        terminalColours.put("purple", 0xFFa74598);
        terminalColours.put("red", 0xFFBC4256);
        terminalColours.put("reset", 0xFFE8E8E8);
        terminalColours.put("orange", 0xFFDE6636);
    }

    public void println(String text) {
        if (isLogging) log(text); // Log the message

        StringBuilder content = new StringBuilder();
        line_iterator:
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') {
                for (int c = 0; c < terminalColours.size(); c++) {
                    String value = String.valueOf(terminalColours.keySet().toArray()[c]);
                    try {
                        if (text.substring(i + 1, i + 1 + value.length()).equals(value)) {
                            if (text.charAt(i + value.length() + 1) == '>') {
                                content.append(value);

                                i = i + (value.length() - 1);

                                text = text.replaceFirst("<" + value + ">", "");
                                continue line_iterator;
                            }
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }
                }
            }
            content.append(" ");
        }
        terminalContents.add(text);
        colouring.add(content.toString());

        pack();
    }

    public void clear() {
        terminalContents.clear();
        colouring.clear();
    }

    private void log(String text) {
        for (int i = 0; i < terminalColours.size(); i++) {
            String value = String.valueOf(terminalColours.keySet().toArray()[i]);
            text = text.replaceAll("<" + value + ">", "");
        }
        System.out.println(text);
    }

    private void pack() {
        if (terminalContents.size() >= 5) {
            ArrayList<String> tmpContents = new ArrayList<>();
            ArrayList<String> tmpColouring = new ArrayList<>();
            for (int i = terminalContents.size() - (5 - 1); i < terminalContents.size(); i++) {
                tmpContents.add(terminalContents.get(i));
                tmpColouring.add(colouring.get(i));
            }
            terminalContents = tmpContents;
            colouring = tmpColouring;
        }
    }

    public void update(EngineAPI api, float dt) {
        if (api.getInput().isKeyDown(KeyEvent.VK_F3) && api.getSettings().isDebug()) {
            isEnabled = !isEnabled;
        }
    }

    public void render(EngineAPI api, Renderer r) {
        if (isEnabled) {
            r.drawFillRect(0, 0, api.getWidth(), 50, 0x56454545);
            for (int ln = 0; ln < terminalContents.size(); ln++) {

                int offset = 0;
                int currentColour = terminalColours.get("reset");

                for (int c = 0; c < terminalContents.get(ln).length(); c++) { // Iterate over each character
                    for (int col = 0; col < terminalColours.size(); col++) { // Iterate over each colour
                        String value = String.valueOf(terminalColours.keySet().toArray()[col]);
                        if (colouring.get(ln).startsWith(value, c)) {
                            currentColour = terminalColours.get(value);
                        }
                    }
                    r.drawText(String.valueOf(terminalContents.get(ln).charAt(c)), offset, ln * r.getFont().getFontHeight(), currentColour);
                    offset += r.getFont().getTextLength(String.valueOf(terminalContents.get(ln).charAt(c)));
                }
            }
        }
    }

    public void enableLogging() {
        this.isLogging = true;
    }

    public void disableLogging() {
        this.isLogging = false;
    }
}
