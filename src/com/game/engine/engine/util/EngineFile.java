package com.game.engine.engine.util;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class EngineFile {

    private String path;

    public EngineFile(String path) {
        this.path = path;
    }

    public void write(String contents) {
        try {
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(contents);
            myWriter.close();
        } catch (IOException ignored) {}
    }

    public String read()
    {
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
        }catch (Exception e) {}
        return "";
    }

    public boolean create() {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch(IOException ignored) {}
        return false;
    }

}
