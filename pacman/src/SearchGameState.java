package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchGameState {
    private final Location currentLocation;
    private final Location target;
    private ArrayList<Location> path;
    private CollisionChecker validator;
    private CollisionChecker portalCollisions;
    private List<Portal> portals;

    public SearchGameState(Location currentLocation, Location target, CollisionChecker validator, List<Location> path,
                           CollisionChecker portalCollisions, List<Portal> portals) {
        this.currentLocation = currentLocation;
        this.target = target;
        this.validator = validator;
        this.path = new ArrayList<>(path);
        this.path.add(this.currentLocation);
        this.portalCollisions = portalCollisions;
        this.portals = portals;
    }

    public ArrayList<SearchGameState> getNextStates() {
        ArrayList<SearchGameState> nextStates = new ArrayList<>();

        for (Location next : currentLocation.getNeighbourLocations(0.5)) {
            if (!validator.collide(next)) {
                Location newNext = new Location(next);
                nextStates.add(new SearchGameState(newNext, this.target, validator, path, portalCollisions, portals));
            }
        }

        return nextStates;
    }

    public boolean isGoalState() {
        return currentLocation.equals(target);
    }

    public ArrayList<Location> getPath() {
        return path;
    }

    public Location getCurrentLocation() {
        if (path.size() > 0)
        {
            System.out.println("Last: " + path.get(path.size() - 1));
        }
        return currentLocation;
    }
}
