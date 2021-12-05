package com.tenebris.engine.engine.util;

public class FileHelper {

    public static String getUseablePath(String path) {

        String result = path;

        if(path.startsWith("res://")) {
            result = "/" + result.substring(6);
        }

        return result;
    }

}
