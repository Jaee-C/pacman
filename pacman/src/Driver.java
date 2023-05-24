package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import javax.swing.*;
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

    private static GameChecker gameChecker;
    private static LevelChecker levelChecker;
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
        if (args.length > 0) {
            propertiesPath = args[0];
            if (!(Arrays.asList("test", "edit")).contains(args[1])) {
                gameCallback.invalidMode();
            } else if (args[1].equals("test")) {
                mode = DriverMode.TEST;
            }
        }

        gameChecker = new GameChecker(gameCallback);
        levelChecker = new LevelChecker(gameCallback);

        properties = PropertiesLoader.loadPropertiesFile(propertiesPath);

        if (mode == DriverMode.TEST && game == null) {
            if (controller != null) {
                controller.close();
            }
            controller = null;
            System.out.println("Test Mode");
            List<String> fileNames = gameChecker.getGames();
            List<PacManGameGrid> gameGrids = levelChecker.checkLevels(fileNames);
            if (gameGrids.size() > 0) {
                game = new Game(gameCallback, properties, gameGrids.get(0));
            }

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
            if (controller != null) {
                controller.close();
            }
            controller = null;

            List<String> fileNames = gameChecker.getGames();
            List<PacManGameGrid> gameGrids = levelChecker.checkLevels(fileNames);
            if (gameGrids.size() > 0) {
                game = new Game(gameCallback, properties, gameGrids.get(0));
            }


        } else if (mode == DriverMode.EDIT && controller == null) {
            System.out.println("Edit Mode");
            controller = new Controller();
            if (game != null) {
                game.close();
            }
            game = null;
        }
    }

//        if (mode == DriverMode.TEST) {
//            toTestMode();
//        } else {
//            toEditMode();
//        }
//    }

    public static void toTestMode() {
        System.out.println("!!!!!!!!!!! Test Mode !!!!!!!!!!!!!");
        System.out.println("  game: " + game + ", controller: " + controller);
        if (controller != null) {
//            controller.close();
        }

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
            game = new Game(gameCallback, properties, gameGrids.get(counter));

            game.run();
            counter++;
        }
    }

    public static void toEditMode() {
        System.out.println("!!!!!!!!! Edit Mode !!!!!!!!!!!");
        mode = DriverMode.EDIT;
        controller = new Controller();
        System.out.println("  game: " + game + ", controller: " + controller);
        if (game != null) {
            System.out.println("Yes");
//            game.close();
        }
        game = null;
    }
}
