package com.game.engine.engine.util;

public class Logger {

    private static String title = "";

    private static String log = "";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final int INFO = 0;
    public static final int ERROR = 1;
    public static final int FATAL_ERROR = 2;

    private static String INFO_TEXT;
    private static String ERROR_TEXT;
    private static  String FATAL_ERROR_TEXT;

    public void init(String gameTitle) {
        title = gameTitle;
        INFO_TEXT = "<" + title + ">: Info - ";
        ERROR_TEXT = "<" + title + ">: Error - ";
        FATAL_ERROR_TEXT = "<" + title + ">: Error - ";
    }

    /**
     * Prints a message to the console
     * @param type Message Type (INFO, ERROR, FATAL_ERROR)
     * @param message Message to log
     */
    public static void log(int type, String message) {
        switch(type) {
            case INFO:
                log += "\n" + INFO_TEXT + message;
                System.out.println(ANSI_GREEN + INFO_TEXT + ANSI_RESET + message);
                return;
            case ERROR:
                log += "\n" + ERROR_TEXT + message;
                System.out.println(ANSI_YELLOW + ERROR_TEXT + ANSI_RESET + message);
                return;
            case FATAL_ERROR:
                log += "\n" + FATAL_ERROR_TEXT + message;
                System.out.println(ANSI_RED + FATAL_ERROR_TEXT + ANSI_RESET + message);
                return;
            default:
                log += "\n" + INFO_TEXT + message;
                System.out.println(ANSI_GREEN + INFO_TEXT + ANSI_RESET + message);
        }
    }
}
