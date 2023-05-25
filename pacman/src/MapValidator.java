package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class MapValidator {
    private List<Location> wallLocations;
    private PortalStore portals;

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
        ArrayList<Location> target = setupEntitiesLocation(tempGame, grid);
        CollisionChecker collisionChecker = new BoundsCollisionChecker(grid.getNbHorzCells(), grid.getNbVertCells());
        collisionChecker.setCollisionLocations(wallLocations);

        List<Location> tobeVisited = new ArrayList<>();
        List<Location> visited = new ArrayList<>();
        visited.add(pacmanStartLocation);

        Location next;

        do {
            next = null;

            for (Location l : pacman.getLocation().getNeighbourLocations(0.5)) {
                boolean repeated = visited.contains(l);

                if (repeated)
                    continue;

                if (!collisionChecker.collide(l)) {
                    if (next == null)
                        next = l;
                    else
                        tobeVisited.add(l);
                }
            }

            if (next == null && !tobeVisited.isEmpty()) {
                next = tobeVisited.get(0);
                tobeVisited.remove(0);
            }

            if (next == null) {
                tempGame.doPause();
                return target;
            }

            next = portals.checkAndTeleport(next);
            pacman.setLocation(next);

            visited.add(next);
            target.remove(next);

            if (target.isEmpty()) {
                tempGame.doPause();
                return target;
            }

        } while (true);
    }

    private ArrayList<Location> setupEntitiesLocation(GameGrid game, PacManGameGrid grid) {
        ArrayList<Location> pillAndItemLocations = new ArrayList<>();
        this.wallLocations = new ArrayList<>();
        this.portals = new PortalStore();
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
                if (a == GameGridCell.Portal_White) {
                    portals.put(game, PortalColour.WHITE, location);
                }
                if (a == GameGridCell.Portal_Yellow) {
                    portals.put(game, PortalColour.YELLOW, location);
                }
                if (a == GameGridCell.Portal_Dark_Gold) {
                    portals.put(game, PortalColour.DARK_GOLD, location);
                }
                if (a == GameGridCell.Portal_Dark_Grey) {
                    portals.put(game, PortalColour.DARK_GREY, location);
                }
            }
        }
        return pillAndItemLocations;
    }
}
