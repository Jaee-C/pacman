package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class BFSMover implements IMover {
    private BFS searcher = new BFS();
    private MoveValidator moveValidator;
    private List<Location> path = new ArrayList<>();

    @Override
    public Location move(Actor movingActor, ArrayList<Location> items) {
        if (path.isEmpty() && !items.isEmpty()) {
            Location nextItem = closestPillLocation(movingActor, items);
            System.out.println("Next: " + nextItem);
            System.out.println("Initial Location: " + movingActor.getLocation());
            SearchGameState gameState = new SearchGameState(movingActor.getLocation(), nextItem, moveValidator, new ArrayList<>());
            path = searcher.search(gameState);
        }

        if (path.isEmpty()) {
            return movingActor.getLocation();
        }
        Location next = path.remove(0);
        return next;
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    private Location closestPillLocation(Actor movingActor, ArrayList<Location> items) {
        int currentDistance = 1000;
        Location currentLocation = null;
        List<Location> pillAndItemLocations = items;
        for (Location location: pillAndItemLocations) {
            int distanceToPill = location.getDistanceTo(movingActor.getLocation());
            if (distanceToPill < currentDistance) {
                currentLocation = location;
                currentDistance = distanceToPill;
            }
        }

        return currentLocation;
    }
}
