package src.utility;

import ch.aplu.jgamegrid.Location;
import src.Monster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.format.SignStyle;
import java.util.List;

/**
 * Please do not change this class. This class is used for testing and your code needs to print the correct output to pass the test
 */
public class GameCallback {
    private String logFilePath = "Log.txt";
    private FileWriter fileWriter = null;

    public GameCallback() {
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void writeString(String str) {
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void endOfGame(String gameResult) {
        writeString(gameResult);
    }

    public void pacManLocationChanged(Location pacmanLocation, int score, int nbPills) {
        String pacmanLocationString = String.format("[PacMan] Location: %d-%d. Score: %d. Pills: %d", pacmanLocation.getX(),
                pacmanLocation.getY(), score, nbPills);
        writeString(pacmanLocationString);
    }

    public void monsterLocationChanged(Monster monster) {
        String monsterLocationString = String.format("[%s] Location: %d-%d", monster.getType(),
                monster.getLocation().getX(), monster.getLocation().getY());
        writeString(monsterLocationString);
    }

    public void pacManEatPillsAndItems(Location pacmanLocation, String type) {
        String pillOrItemLocationString = String.format("[PacMan] Location: %d-%d. Eat Pill/Item: %s", pacmanLocation.getY(),
                pacmanLocation.getY(), type);
        writeString(pillOrItemLocationString);
    }

    public void gameCheckNoMapsFound() {
        writeString("[Game foldername - no maps found]");
    }

    public void gameCheckMultipleMapsSameLevel(List<String> maps) {
        String s = String.format("[Game foldername - multiple maps at same level: %s]", String.join(";", maps));
        writeString(s);
    }

    public void levelCheckNoPacmanStart(String fileName) {
        String s = String.format("[%s - no start for PacMan]",fileName);
        writeString(s);
    }

    public void levelCheckMultiplePacmanStart(String fileName, List<String> coordinates) {
        String s = String.format("[%s - more than one start for Pacman: %s]",fileName, coordinates);
        writeString(s);
    }

    public void levelCheckPortalError(String fileName, String portalName, List<String> coordinates) {
        String s = String.format("[%s - portal %s count is not: %s]",fileName, portalName, coordinates);
        writeString(s);
    }

    public void levelCheckGoldPillError(String fileName) {
        String s = String.format("[%s - less than 2 Gold and Pill]", fileName);
        writeString(s);
    }

    public void levelCheckNotAccessible(String fileName, String GoldOrPill, List<String> coordinates) {
        String s = String.format("[%s - %s not accessible: %s]", fileName, GoldOrPill, coordinates);
        writeString(s);
    }

    public void invalidMode() {
        writeString("[Invalid Mode Argument. Mode argument must be `edit` or `test`]");
    }
}
