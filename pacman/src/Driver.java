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
            } else if (args[1] == "test") {
                mode = DriverMode.TEST;
            }
        }

        properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        GameChecker gameChecker = new GameChecker(gameCallback);
        LevelChecker levelChecker = new LevelChecker(gameCallback);

        List<String> fileNames = gameChecker.getGames();
        List<PacManGameGrid> gameGrids = levelChecker.checkLevels(fileNames);

        if (gameGrids.size() == 0) {
            System.out.println("No valid levels found");
            return;
        }

            if (mode == DriverMode.TEST && game == null) {
                System.out.println("Test Mode");
                game = new Game(gameCallback, properties);
                if (controller != null) {
                    controller.close();
                }
                controller = null;
            } else if (mode == DriverMode.EDIT && controller == null) {
                System.out.println("Edit Mode");
                controller = new Controller();
                if (game != null) {
                    game.close();
                }
                game = null;
            }

    }

    public static void changeMode() {
        if (mode == DriverMode.TEST) {
            System.out.println("  Change from TEST to EDIT");
            mode = DriverMode.EDIT;
        } else if (mode == DriverMode.EDIT) {
            System.out.println("  Change from EDIT to TEST");
            mode = DriverMode.TEST;
        }

        if (mode == DriverMode.TEST && game == null) {
            System.out.println("Test Mode");
            game = new Game(gameCallback, properties);
            if (controller != null) {
                controller.close();
            }
            controller = null;
        } else if (mode == DriverMode.EDIT && controller == null) {
            System.out.println("Edit Mode");
            controller = new Controller();
            if (game != null) {
                game.close();
            }
            game = null;
        }
    }
}
