package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MapValidator {
    private static final MapValidator instance = null;

    public static MapValidator getInstance() {
        if (instance == null) {
            return new MapValidator();
        }
        return instance;
    }

    public boolean isValid(Location pacmanStartLocation, PacManGameGrid grid) {
        if (pacmanStartLocation == null) {
            return true;
        }

        Actor pacman = new Actor();
        ArrayList<Location> target = setupPillAndItemsLocations(grid);
        MoveValidator moveValidator = new MoveValidator(pacman, Game.nbHorzCells, Game.nbVertCells);
        IMover automover = new ClosestPillMover();
        automover.setMoveValidator(moveValidator);
        pacman.setLocation(pacmanStartLocation);

        List<Location> tobeVisited = new ArrayList<>();
        HashSet<Location> visited = new HashSet<>();

        Location next;

        do {
            next = null;

            for (Location l : pacman.getLocation().getNeighbourLocations(0.5)) {
                if (visited.contains(l)) {
                    continue;
                }

                if (moveValidator.canMove(l)) {
                    if (next == null) {
                        next = l;
                    }
                    tobeVisited.add(l);
                }
            }

            if (next == null && !tobeVisited.isEmpty()) {
                next = tobeVisited.get(0);
                Location automoverNext = automover.move(pacman, next);
                while (!automoverNext.equals(next)) {
                    next = automoverNext;
                    automoverNext = automover.move(pacman, next);
                }

            }

            if (next == null) {
                return false;
            }

            pacman.setLocation(next);
            visited.add(next);
            target.remove(next);

            if (target.isEmpty()) {
                return true;
            }

        } while (!tobeVisited.isEmpty());

        return false;
    }

    private ArrayList<Location> setupPillAndItemsLocations(PacManGameGrid grid) {
        ArrayList<Location> pillAndItemLocations = new ArrayList<>();
        for (int y = 0; y < Game.nbVertCells; y++)
        {
            for (int x = 0; x < Game.nbHorzCells; x++)
            {
                Location location = new Location(x, y);
                int a = grid.getCell(location);
                if (a == 1) {
                    pillAndItemLocations.add(location);
                }
                if (a == 3) {
                    pillAndItemLocations.add(location);
                }
                if (a == 4) {
                    pillAndItemLocations.add(location);
                }
            }
        }
        return pillAndItemLocations;
    }
}
