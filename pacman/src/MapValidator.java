package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapValidator {
    private List<Location> wallLocations;
    private FileWriter fileWriter;

    private void writeString(String str) {
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Location> checkMap(PacManGameGrid grid) {
        // Temp game is required for actor to perform actions, such as updating location
        GameGrid tempGame = new GameGrid(grid.getNbHorzCells(), grid.getNbVertCells(), 30, false);
        Location pacmanStartLocation = grid.getPacManStartLocation();
        if (pacmanStartLocation == null) {
            return new ArrayList<>();
        }

        try {
            fileWriter = new FileWriter(new File("test.txt"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int i = 0;

        Actor pacman = new Actor();
        tempGame.doRun();
        tempGame.addActor(pacman, pacmanStartLocation);
        ArrayList<Location> target = setupPillAndItemsLocations(grid);
        MoveValidator moveValidator = new MoveValidator(pacman, grid.getNbHorzCells(), grid.getNbVertCells());
        moveValidator.setInvalidLocations(wallLocations);
        IMover automover = new ClosestPillMover();
        automover.setMoveValidator(moveValidator);

        List<Location> tobeVisited = new ArrayList<>();
        List<Location> visited = new ArrayList<>();

        Location next;

        return new ArrayList<>();

        // TODO: This is not working, need a MoveValidator that doesn't depend on background colors
//        do {
//            next = null;
//
//            for (Location l : pacman.getLocation().getNeighbourLocations(0.5)) {
//                boolean repeated = visited.contains(l);
//
//                if (repeated)
//                    continue;;
//
//                if (moveValidator.canMove(l)) {
//                    if (next == null) {
//                        next = l;
//                    }
//                    tobeVisited.add(l);
//                }
//            }
//            System.out.println("Surrounding locations: " + next);
//
//            if (next == null && !tobeVisited.isEmpty()) {
//                next = tobeVisited.get(0);
//                tobeVisited.remove(0);
//                System.out.println("To be visited: " + tobeVisited);
//            }
//
//            if (next == null) {
//                tempGame.doPause();
//                return target;
//            }
//
//            pacman.setLocation(next);
//            visited.add(next);
//            target.remove(next);
//
//            if (target.isEmpty()) {
//                tempGame.doPause();
//                return target;
//            }
//            i++;
//            System.out.println("Pacman Location: " + next);
//
//            if (i > 100) {
//                return target;
//            }
//
//
//
//        } while (!tobeVisited.isEmpty());
//
//        return target;
    }

    private ArrayList<Location> setupPillAndItemsLocations(PacManGameGrid grid) {
        ArrayList<Location> pillAndItemLocations = new ArrayList<>();
        this.wallLocations = new ArrayList<>();
        for (int y = 0; y < Game.nbVertCells; y++)
        {
            for (int x = 0; x < Game.nbHorzCells; x++)
            {
                Location location = new Location(x, y);
                GameGridCell a = grid.getCell(location);
                if (a == GameGridCell.Pill || a == GameGridCell.Gold) {
                    pillAndItemLocations.add(location);
                }
                if (a == GameGridCell.Wall) {
                    wallLocations.add(location);
                }

            }
        }
        return pillAndItemLocations;
    }
}
