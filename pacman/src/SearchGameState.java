package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class SearchGameState {
    private final Location currentLocation;
    private final Location target;
    private ArrayList<Location> path;
    private MoveValidator validator;

    public SearchGameState(Location currentLocation, Location target, MoveValidator validator, ArrayList<Location> path) {
        this.currentLocation = currentLocation;
        this.target = target;
        this.validator = validator;
        this.path = new ArrayList<>(path);
        this.path.add(this.currentLocation);
    }

    public ArrayList<SearchGameState> getNextStates() {
        ArrayList<SearchGameState> nextStates = new ArrayList<>();

        for (Location next : currentLocation.getNeighbourLocations(0.5)) {
            if (validator.canMove(next)) {
                Location newNext = new Location(next);
                nextStates.add(new SearchGameState(newNext, this.target, validator, path));
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

    public void printPath() {
        System.out.print("Path: ");
        for (Location move: path) {
            System.out.print(move + " ");
        }
        System.out.println();
    }
}
