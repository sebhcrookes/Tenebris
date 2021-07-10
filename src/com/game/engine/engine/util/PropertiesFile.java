package com.game.engine.engine.util;

public class PropertiesFile {

    private String[] splitContent;

    public PropertiesFile(String path) {
        try {
            EngineFile file = new EngineFile(path);

            String fileContent = file.read();
            fileContent = fileContent.replace("\n", "=");

            this.splitContent = fileContent.split("=");
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to load properties file"); }
    }

    /**
     * Get the value assigned to a specific key
     * @param key Key to lookup
     * @return Value assigned to key
     */
    public String get(String key) {
        try {
            for(int i = 0; i < this.splitContent.length; i+=2) {
                if(this.splitContent[i].equals(key)) {
                    return this.splitContent[i+1];
                }
            }
        }catch(Exception ignored) {}
        return "";
    }
}
