package com.game.engine.engine.util;

import com.game.engine.engine.util.terminal.Console;

public class PropertiesFile {

    private String[] splitContent;

    public PropertiesFile(String path) {
        try {
            EngineFile file = new EngineFile(path);

            String fileContent = file.read();
            fileContent = fileContent.replace("\n", "=");

            this.splitContent = fileContent.split("=");
        } catch (Exception e) {
            Console.println("<red>Error: <reset>Failed to load properties file");
        }
    }

    /**
     * Get the value assigned to a specific key
     *
     * @param key Key to lookup
     * @return Value assigned to key
     */
    public String get(String key) {
        try {
            for (int i = 0; i < this.splitContent.length; i += 2) {
                if (this.splitContent[i].equals(key)) {
                    return this.splitContent[i + 1];
                }
            }
        } catch (Exception ignored) {
        }
        return "";
    }

    public int getInteger(String key) {
        String integer = get(key);
        return Integer.parseInt(integer);
    }

    public long getHex(String key) {
        String hex = get(key);
        if (hex.startsWith("0x")) {
            hex = hex.substring(2);
        } else if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else {
            return 0;
        }

        long convertedValue = Long.parseLong(hex, 16);
        return convertedValue;
    }

    public double getDouble(String key) {
        String d = get(key);

        double convertedValue = Double.parseDouble(d);
        return convertedValue;
    }
}
