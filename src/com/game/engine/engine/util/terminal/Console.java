package com.game.engine.engine.util.terminal;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;

public class Console {

    private static Terminal terminal;

    public static void init(EngineAPI api) {
        terminal = new Terminal(api);
    }

    public static void println(String text) {
        terminal.println(text);
    }

    public static void clear() {
        terminal.clear();
    }

    public static void update(EngineAPI api, float dt) {
        terminal.update(api, dt);
    }

    public static void render(EngineAPI api, Renderer r) {
        terminal.render(api, r);
    }
}
