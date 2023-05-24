package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Driver {
    enum DriverMode {
        TEST,
        EDIT
    }
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";
    private static Controller controller = null;
    private static Game game = null;
    private static DriverMode mode = DriverMode.EDIT;
    private static Driver instance;
    private static GameCallback gameCallback;
    private static Properties properties;
    private static String loadFilename = null;
    private Driver() {

    }

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
        gameCallback = new GameCallback();
        DriverMode mode = DriverMode.TEST;
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

        properties = PropertiesLoader.loadPropertiesFile(DEFAULT_PROPERTIES_PATH);

        if (mode == DriverMode.TEST) {
            toTestMode();
        } else {
            toEditMode();
        }
    }

    public static void toTestMode() {
        System.out.println("!!!!!!!!!!! Test Mode !!!!!!!!!!!!!");
        mode = DriverMode.TEST;
        GameChecker gameChecker = new GameChecker(gameCallback);
        LevelChecker levelChecker = new LevelChecker(gameCallback);

        List<String> fileNames = gameChecker.getGames();
        List<PacManGameGrid> gameGrids = levelChecker.checkLevels(fileNames);

        if (gameGrids.size() == 0) {
            System.out.println("No valid levels found");
            return;
        }
        int counter = 0;
        while (counter < gameGrids.size()){
            Game curr_level = new Game(gameCallback, properties, gameGrids.get(counter));
            if(curr_level.lose){
                break;
            }
            counter++;
        }

        if (controller != null) {
            controller.close();
        }
    }

    public static void toEditMode() {
        System.out.println("!!!!!!!!! Edit Mode !!!!!!!!!!!");
        mode = DriverMode.EDIT;
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
