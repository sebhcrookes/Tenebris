package com.tenebris.engine.engine.util;

public class Log {

    private static int logLevel = 2;

    public static final int INFO_LEVEL = 2;
    public static final int WARN_LEVEL = 1;
    public static final int ERROR_LEVEL = 0;

    public static void info(String text) {
        if(logLevel >= INFO_LEVEL) {
            System.out.println("<Info>: " + text);
        }
    }

    public static void warn(String text) {
        if(logLevel >= WARN_LEVEL) {
            System.err.println("<Warn>: " + text);
        }
    }

    public static void error(String text) {
        if(logLevel >= ERROR_LEVEL) {
            System.err.println("<Error>: " + text);
        }
    }

    public static void setLogLevel(int level) {
        logLevel = level;
    }

    public static int getLogLevel() {
        return logLevel;
    }
}
