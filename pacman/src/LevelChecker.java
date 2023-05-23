package src;

import src.matachi.mapeditor.editor.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.w3c.dom.*;
import src.utility.GameCallback;

import javax.xml.parsers.*;
import java.io.*;

public class LevelChecker {
    Controller controller;
    GameCallback gameCallback;
    List<String> portalStrings = Arrays.asList("PortalWhiteTile", "PortalYellowTile", "PortalDarkGoldTile", "PortalDarkGrayTile");

    public LevelChecker(Controller controller, GameCallback callback) {
        this.controller = controller;
        this.gameCallback = callback;
    }

    public boolean checkLevels(List<String> fileNames) {
        List<String> res = new ArrayList<>();

        for (String fileName : fileNames) {
            String filePath = "/game/" + fileName;

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

                // If you wanna Create the 2D grid array - uncomment the commented lines
//                String[][] gridArray = new String[rows][columns];

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

                        if (cellValue == "PacTile") {
                            // Store coordinates of PacTiles

                        } else if (cellValue == "GoldTile" || cellValue == "PillTile") {
                            // Count the number of gold and pill tiles
                            goldPillCount++;
                        } else if (portalStrings.contains(cellValue)) {
                            // Count the number of portals of each colour - store coordinates in a hashmap
                            if (portalCoordsMap.containsKey(cellValue)) {
                                List<String> existingList = portalCoordsMap.get(cellValue);
                                existingList.add(coorToString(i, j));
                                portalCoordsMap.put(cellValue, existingList);
                            } else {
                                portalCoordsMap.put(cellValue, Arrays.asList(coorToString(i, j)));
                            }
                        }
//                        gridArray[i][j] = cellValue;
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

                // TODO Convert the XML Map to actual map and run autoplayer

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private String coorToString(int x, int y) {
        return "(" + x + "," + y + ")";
    }
}
