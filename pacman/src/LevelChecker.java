package src;

import ch.aplu.jgamegrid.Location;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.w3c.dom.*;
import src.utility.GameCallback;

import javax.xml.parsers.*;
import java.io.*;

public class LevelChecker {
    GameCallback gameCallback;
    List<String> portalStrings = Arrays.asList("PortalWhiteTile", "PortalYellowTile", "PortalDarkGoldTile", "PortalDarkGrayTile");
    MapValidator mapValidator = new MapValidator();

    public LevelChecker(GameCallback callback) {
        this.gameCallback = callback;
    }

    /**
     * Returns true if the levels pass the 4 validity checks and false otherwise. Also does error logs if validity check is not passed.
     * Level Checks:
     *  1. One starting point for pacman
     *  2. two tiles for each portal
     *  3. at least two gold and pill in total
     *  4. each gold and pill is accessible
     * @param fileNames
     * @return
     */
    public List<PacManGameGrid> checkLevels(List<String> fileNames) {
        List<PacManGameGrid> allGameGrids = new ArrayList<>();

        for (String fileName : fileNames) {
            String filePath = "./game/" + fileName;

            try {
                File inputFile = new File(filePath);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputFile);

                Element rootElement = document.getDocumentElement();
                NodeList rowList = rootElement.getElementsByTagName("row");

                int rows = rowList.getLength();
                int columns = 0;

                // Find the maximum number of cells in a row
                for (int i = 0; i < rows; i++) {
                    Element row = (Element) rowList.item(i);
                    NodeList cellList = row.getElementsByTagName("cell");
                    int cellCount = cellList.getLength();
                    if (cellCount > columns) {
                        columns = cellCount;
                    }
                }

                StringBuilder mazeString = new StringBuilder();

                // Populate the grid array with cell values
                int goldPillCount = 0;
                List<String> pacCoords = new ArrayList<String>();
                Map<String, List<String>> portalCoordsMap = new HashMap<String, List<String>>();
                for (int i = 0; i < rows; i++) {
                    Element row = (Element) rowList.item(i);
                    NodeList cellList = row.getElementsByTagName("cell");
                    for (int j = 0; j < columns; j++) {
                        Element cell = (Element) cellList.item(j);
                        String cellValue = cell.getTextContent();

                        if (cellValue.equals("PacTile")) {
                            // Store coordinates of PacTiles
                            pacCoords.add(coorToString(i, j));
                        } else if (cellValue.equals("GoldTile") || cellValue.equals("PillTile")) {
                            // Count the number of gold and pill tiles
                            goldPillCount++;
                        } else if (portalStrings.contains(cellValue)) {
                            // Count the number of portals of each colour - store coordinates in a hashmap
                            if (portalCoordsMap.containsKey(cellValue)) {
                                List<String> existingList = new ArrayList<>(portalCoordsMap.get(cellValue));
                                existingList.add(coorToString(i, j));
                                portalCoordsMap.put(cellValue, existingList);
                            } else {
                                portalCoordsMap.put(cellValue, List.of(coorToString(i, j)));
                            }
                        }

                        // Encode the cell value into a char
                        char tileNr = 'a';
                        if (cellValue.equals("PathTile"))
                            tileNr = ' ';
                        else if (cellValue.equals("WallTile"))
                            tileNr = 'x';
                        else if (cellValue.equals("PillTile"))
                            tileNr = '.';
                        else if (cellValue.equals("GoldTile"))
                            tileNr = 'g';
                        else if (cellValue.equals("IceTile"))
                            tileNr = 'i';
                        else if (cellValue.equals("PacTile"))
                            tileNr = 'p';
                        else if (cellValue.equals("TrollTile"))
                            tileNr = 't';
                        else if (cellValue.equals("TX5Tile"))
                            tileNr = '5';
                        else if (cellValue.equals("PortalWhiteTile"))
                            tileNr = 'w';
                        else if (cellValue.equals("PortalYellowTile"))
                            tileNr = 'y';
                        else if (cellValue.equals("PortalDarkGoldTile"))
                            tileNr = 'o';
                        else if (cellValue.equals("PortalDarkGrayTile"))
                            tileNr = 'a';
                        else
                            tileNr = '0';    // What does this encode to?

                        mazeString.append(tileNr);
                    }
                }

                // Check only 1 pacman tile
                if (pacCoords.size() == 0) {
                    gameCallback.levelCheckNoPacmanStart(fileName);
                } else if (pacCoords.size() > 1) {
                    gameCallback.levelCheckMultiplePacmanStart(fileName, pacCoords);
                }

                // Check at least 2 gold and pill
                if (goldPillCount < 2) {
                    gameCallback.levelCheckGoldPillError(fileName);
                }

                // Check 2 portals of each colour
                for (String portalName : portalCoordsMap.keySet()) {
                    List<String> portalCoords = portalCoordsMap.get(portalName);
                    if (portalCoords.size() != 2) {
                        gameCallback.levelCheckPortalError(fileName, portalName.replace("Portal", ""), portalCoords);
                    }
                }

                System.out.println(fileName + " Maze: " + mazeString);
                PacManGameGrid gameGrid = new PacManGameGrid(columns, rows, mazeString.toString());

                if (pacCoords.size() == 0) {
                    // No pacman, don't need to check map
                    continue;
                }

                List<Location> unreached = mapValidator.checkMap(gameGrid);
                HashMap<GameGridCell, List<String>> unreachedItems = new HashMap<>();
                if (!unreached.isEmpty()) {
                    for (Location location : unreached) {
                        GameGridCell unreachedCell = gameGrid.getCell(location);
                        if (unreachedItems.containsKey(unreachedCell)) {
                            List<String> existingList = new ArrayList<>(unreachedItems.get(unreachedCell));
                            existingList.add(coorToString(location));
                            unreachedItems.put(unreachedCell, existingList);
                        } else {
                            unreachedItems.put(unreachedCell, List.of(coorToString(location)));
                        }
                    }
                    for (GameGridCell item : unreachedItems.keySet()) {
                        List<String> itemCoords = unreachedItems.get(item);
                        gameCallback.levelCheckNotAccessible(fileName, item.toString(), itemCoords);
                        System.out.println("MAP IS NOT ACCESSIBLE");
                        // TODO: Switch mode
                    }
                }

                allGameGrids.add(gameGrid);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return allGameGrids;
    }

    private String coorToString(int x, int y) {
        return "(" + x + "," + y + ")";
    }
    private String coorToString(Location location) {
        return "(" + location.getX() + "," + location.getY() + ")";
    }
}
