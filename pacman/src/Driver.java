package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

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
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        gameCallback = new GameCallback();
        DriverMode mode = DriverMode.EDIT;
        if (args.length > 0) {
            propertiesPath = args[0];
            if (!(Arrays.asList("test", "edit")).contains(args[1])) {
                gameCallback.invalidMode();
            } else if (args[1].equals("test")) {
                mode = DriverMode.TEST;
            }
        }

        properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

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
            new Game(gameCallback, properties, gameGrids.get(counter));
            counter++;
        }

        game = new Game(gameCallback, properties, gameGrids.get(counter));
        if (controller != null) {
            controller.close();
        }
    }

    public static void toEditMode() {
        System.out.println("!!!!!!!!! Edit Mode !!!!!!!!!!!1");
        mode = DriverMode.EDIT;
        controller = new Controller();
        if (game != null) {
            game.close();
        }
        game = null;
    }
}
