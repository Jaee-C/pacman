package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class SearchGameState {
    private final Location currentLocation;
    private final Location target;
    private ArrayList<Location> path;
    private CollisionChecker validator;
    private PortalStore portals;

    public SearchGameState(Location currentLocation, Location target, CollisionChecker validator, List<Location> path,
                           PortalStore portals) {
        this.currentLocation = currentLocation;
        this.target = target;
        this.validator = validator;
        this.path = new ArrayList<>(path);
        this.path.add(this.currentLocation);
        this.portals = portals;
    }

    public ArrayList<SearchGameState> getNextStates() {
        ArrayList<SearchGameState> nextStates = new ArrayList<>();

        for (Location next : currentLocation.getNeighbourLocations(0.5)) {
            if (!validator.collide(next)) {
                Location newNext = new Location(next);

                newNext = portals.checkAndTeleport(newNext);
                nextStates.add(new SearchGameState(newNext, this.target, validator, path, portals));
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
}
