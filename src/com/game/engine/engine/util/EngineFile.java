package com.game.engine.engine.util;

import java.io.*;

public class EngineFile {

    private String path;

    public EngineFile(String path) {

        if(new File(path).isDirectory() || new File(path).exists()) {
            this.path = path;
        } else {
            try {
                new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
                this.path = path;
            } catch (Exception ignored) {
                this.path = path;
            }
        }
    }

    public void write(String contents) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException ignored) {}
    }

    public String read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            return stringBuilder.toString();
        } catch (Exception e) {
        }
        return "";
    }

    public boolean create() {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch (IOException ignored) {
        }
        return false;
    }

    public boolean createDir() {
        File file = new File(path);
        try {
            file.mkdir();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
