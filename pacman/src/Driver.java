package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import javax.swing.*;
import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Driver {
    public enum DriverMode {
        TEST,
        EDIT
    }
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";
    private static Controller controller = null;
    private static Game game = null;
    private static DriverMode mode;
    private static Driver instance;
    private static GameCallback gameCallback;
    private static Properties properties;

    private static GameChecker gameChecker;
    private static LevelChecker levelChecker;
    private static String loadFilename = null;
    private static String gameRes = "";
    private static int counter = 0;
    private static List<PacManGameGrid> gameGrids;
    private Driver() {}

    public static Driver getInstance() {
        if (instance == null) {
            instance = new Driver();
        }
        return instance;
    }

    /**
     * Starting point
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        gameCallback = new GameCallback();
        if (args.length == 0) {
            mode = DriverMode.EDIT;
        } else {
            Pattern xmlPattern = Pattern.compile("\\.xml$");
            Matcher xmlMatcher = xmlPattern.matcher(args[0]);

            // Skip non-xml files
            if (xmlMatcher.find()) {
                // Open this file in edit mode
                mode = DriverMode.EDIT;
                loadFilename = args[0];
            } else {
                // open in test mode
                mode = DriverMode.TEST;
            }
        }

        gameChecker = new GameChecker(gameCallback);
        levelChecker = new LevelChecker(gameCallback);

        properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        runMode();

    }

    public void setMode(DriverMode mode) {
        this.mode = mode;
        loadFilename = null;
        runMode();
    }

    public void setMode(DriverMode mode, String filename) {
        this.mode = mode;
        loadFilename = filename;
        runMode();
    }

    public static void runMode() {
        if (mode == DriverMode.TEST && game == null) {
            if (controller != null) {
                controller.close();
            }
            controller = null;
            List<String> fileNames = gameChecker.getGames();
            gameGrids = levelChecker.checkLevels(fileNames);

            if (gameGrids.size() > 0) {
                if (game == null) {
                    counter = 0;
                }
                game = new Game(gameCallback, properties, gameGrids.get(counter));
            }

        } else if (mode == DriverMode.EDIT && controller == null) {
            controller = new Controller();
            if (loadFilename != null) {
                controller.loadFile(loadFilename);
            }
            if (game != null) {
                game.close();
            }
            game = null;
        }
    }

    public void nextLevel() {
        counter++;
        if (counter < gameGrids.size()) {
            game.close();
            game = new Game(gameCallback, properties, gameGrids.get(counter));
        } else {
            this.setMode(DriverMode.EDIT);
        }
    }

    public static LevelChecker getLevelChecker() {
        return levelChecker;
    }
}
