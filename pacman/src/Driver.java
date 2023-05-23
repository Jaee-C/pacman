package src;

import src.matachi.mapeditor.editor.Controller;
import src.utility.GameCallback;
import src.utility.PropertiesLoader;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */

    public static void main(String args[]) {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        GameCallback gameCallback = new GameCallback();

        // TODO Check if gameChecker Works - XY Laptop can't run the 2D Map stuff ;-;
//        GameChecker gameChecker = new GameChecker(gameCallback);
//        List<String> fileNames = gameChecker.getGames();
//        System.out.println("Filenames: " + fileNames);

        // TODO Check if LevelChecker Works
//        LevelChecker levelChecker = new LevelChecker(controller, gameCallback)
//        if (levelChecker.checkLevels(fileNames)) {
//            // then @rel can use fileNames to be played
//        }

        new Game(gameCallback, properties);
    }
}
