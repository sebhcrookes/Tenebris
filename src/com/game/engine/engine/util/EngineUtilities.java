package com.game.engine.engine.util;

public class EngineUtilities {

    @Deprecated
    public static long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    @Deprecated
    public static long getUsedMemory() {
        Runtime t = Runtime.getRuntime();
        return t.totalMemory() - t.freeMemory();
    }
}
