package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapValidator {
    public ArrayList<Location> checkMap(PacManGameGrid grid) {
        // Temp game is required for actor to perform actions, such as updating location
        GameGrid tempGame = new GameGrid(grid.getNbHorzCells(), grid.getNbVertCells(), 30, false);
        Location pacmanStartLocation = grid.getPacManStartLocation();
        if (pacmanStartLocation == null) {
            return new ArrayList<>();
        }

        Actor pacman = new Actor();
        tempGame.doRun();
        tempGame.addActor(pacman, pacmanStartLocation);
        ArrayList<Location> target = setupPillAndItemsLocations(grid);
        MoveValidator moveValidator = new MoveValidator(pacman, grid.getNbHorzCells(), grid.getNbVertCells());
        IMover automover = new ClosestPillMover();
        automover.setMoveValidator(moveValidator);

        List<Location> tobeVisited = new ArrayList<>();
        HashSet<Location> visited = new HashSet<>();

        Location next;

        return new ArrayList<>();

        // TODO: This is not working, need a MoveValidator that doesn't depend on background colors
//        do {
//            next = null;
//
//            for (Location l : pacman.getLocation().getNeighbourLocations(0.5)) {
//                if (visited.contains(l)) {
//                    continue;
//                }
//
//                if (moveValidator.canMove(l)) {
//                    if (next == null) {
//                        next = l;
//                    }
//                    tobeVisited.add(l);
//                }
//            }
//
//            if (next == null && !tobeVisited.isEmpty()) {
//                next = tobeVisited.get(0);
//                Location automoverNext = automover.move(pacman, next);
//                while (!automoverNext.equals(next)) {
//                    next = automoverNext;
//                    automoverNext = automover.move(pacman, next);
//                }
//
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
//
//        } while (!tobeVisited.isEmpty());
//
//        return target;
    }

    private ArrayList<Location> setupPillAndItemsLocations(PacManGameGrid grid) {
        ArrayList<Location> pillAndItemLocations = new ArrayList<>();
        for (int y = 0; y < Game.nbVertCells; y++)
        {
            for (int x = 0; x < Game.nbHorzCells; x++)
            {
                Location location = new Location(x, y);
                GameGridCell a = grid.getCell(location);
                if (a == GameGridCell.Pill || a == GameGridCell.Gold) {
                    pillAndItemLocations.add(location);
                }
            }
        }
        return pillAndItemLocations;
    }
}
