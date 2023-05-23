package src;

import src.utility.GameCallback;

import java.io.File;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameChecker {
    GameCallback gameCallback;

    public GameChecker(GameCallback gameCallback) {
        this.gameCallback = gameCallback;
    }

    public List<String> getGames() {
        List<String> res = new ArrayList<>();
        String folderPath = "./game";

        // Create a File object representing the folder
        File folder = new File(folderPath);

        // Report Error if folder does not exist
        if (!folder.exists() || !folder.isDirectory()) {
            // Error Log
            gameCallback.gameCheckNoMapsFound();
            // TODO Switch Mode

            return res;
        }

        // List files in the folder
        File[] files = folder.listFiles();

        // Report Error if folder is empty
        if (files == null) {
            // Error log
            gameCallback.gameCheckNoMapsFound();
            // TODO Switch Mode

            return res;
        }

        // Add filenames to a map of levelNum-arrayOfLevelNames - finding for duplicate level numbers
        Map<Integer, List<String>> levelsMap = new HashMap<>();
        for (File file : files) {
            String fileName = file.getName();

            if (fileName.equals("2_ErrorMaplog.txt")) {
                continue;
            }

            // Extract the number using regex
            Pattern pattern = Pattern.compile("^\\d+");
            Matcher matcher = pattern.matcher(fileName);

            if (matcher.find()) {
                int number = Integer.parseInt(matcher.group());
                if (levelsMap.containsKey(number)) {
                    List<String> existingList = levelsMap.get(number);
                    existingList.add(fileName);
                    levelsMap.put(number, existingList);
                } else {
                    List<String> newList = new ArrayList<>();
                    newList.add(fileName);
                    levelsMap.put(number, newList);
                }
            }
        }

        // Report Error if no files start with a number
        if (levelsMap.size() == 0) {
            // Error log
            gameCallback.gameCheckNoMapsFound();
            // TODO Switch Mode

            return res;
        }

        // Report Error if multiple files of the same level number
        for (List<String> val : levelsMap.values()) {
            if (val.size() == 1) {
                res.addAll(val);
            } else {
                // Error log
                gameCallback.gameCheckMultipleMapsSameLevel(val);
                // TODO Switch Mode

                res.clear();
                return res;
            }
        }

        return res;
    }
}
