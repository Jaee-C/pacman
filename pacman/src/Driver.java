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
    private static String loadFilename = null;
    private static String gameRes = "";
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

    public static void changeMode() {
        if (mode == DriverMode.TEST) {
            System.out.println("  Change from TEST to EDIT");
            mode = DriverMode.EDIT;

        } else if (mode == DriverMode.EDIT) {
            System.out.println("  Change from EDIT to TEST");
            mode = DriverMode.TEST;
        }

        runMode();
    }

    public static void runMode() {
        if (mode == DriverMode.TEST && game == null) {
            if (controller != null) {
                controller.close();
            }
            controller = null;
            System.out.println("!!!!!!1 Test Mode !!!!!!!!11");
            List<String> fileNames = gameChecker.getGames();
            List<PacManGameGrid> gameGrids = levelChecker.checkLevels(fileNames);

            game = new Game(gameCallback, properties, gameGrids.get(0));
            /*
            if (gameGrids.size() > 0) {
                int counter = 0;
                while (counter < gameGrids.size()){
                    if (game == null) {
                        game = new Game(gameCallback, properties, gameGrids.get(counter));
                    }
//                    String gameRes;
//                    SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
//                        @Override
//                        public String doInBackground() throws Exception {
//                            String val = game.isEnd();
//                            while(!val.equals("Pacman Hit") && !val.equals("Win")) {
//                                val = game.isEnd();
//                            }
//                            return val;
//                        }
//
//                        @Override
//                        public void done() {
//                            try {
//                                gameRes = get();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//                    worker.execute();

//                    if (gameRes.equals("Pacman Hit")) {
//                        System.out.println("!!!!!!!1 PACMAN HITIITITIT");
//                        changeMode();
//                    }

//                    if (game.isEnd() == "Pacman Hit") {
//                        changeMode();
//                    } else if (game.isEnd() == "Win") {
//                        game.close();
//                        game = null;
//                        counter ++;
//                    }
                }
            }
            */

        } else if (mode == DriverMode.EDIT && controller == null) {
            System.out.println("!!!!!!111 Edit Mode !!!!!!!!!1");
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
}
