package com.game.engine.engine.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public boolean create() {
        File file = new File(path);
        try {
            return file.createNewFile();
        } catch(IOException ignored) {}
        return false;
    }

}
