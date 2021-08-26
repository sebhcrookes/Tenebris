package com.tenebris.engine.engine.util;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorHandler {

    public static void createErrorPane(Exception e) {
        String message = e.toString();
        String stackTraceElement = e.getStackTrace()[0].toString();

        // Extracting the class name
        List<String> matchList = new ArrayList<>();
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        Matcher regexMatcher = regex.matcher(stackTraceElement);

        while (regexMatcher.find()) { // Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1)); // Fetching Group from String
        }

        String classCause = matchList.get(matchList.size() - 1);

        try {
            JOptionPane.showMessageDialog(new JFrame(), message + "\nCaused by: '" + classCause + "'", "Fatal Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch(HeadlessException he) { // If GraphicsEnvironment.isHeadless returns true (why would you be running a game in headless?)
            System.out.println(message + "\nCaused by: '" + classCause + "'");
        }

        System.exit(1);
    }
}
