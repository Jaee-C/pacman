package src;

import java.util.ArrayList;
import java.util.List;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class ClosestPillMover implements IMover {
    private CollisionChecker collisionChecker;
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private final int listLength = 3;

    @Override
    public Location move(Actor movingActor, Location target) {
        Location currLocation = movingActor.getLocation();

        // Move towards the nearest pill or gold
        int minDistance = Integer.MAX_VALUE;
        Location minLocation = null;
        for (Location location : currLocation.getNeighbourLocations(0.5)) {
            if (isVisited(location) || collisionChecker.collide(location)) {
                continue;
            }
            int distance = location.getDistanceTo(target);
            if (distance < minDistance) {
                minDistance = distance;
                minLocation = location;
            }
        }
        addVisitedList(minLocation);
        return minLocation;
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }


    private void addVisitedList(Location location)
    {
        visitedList.add(location);
        if (visitedList.size() == listLength)
            visitedList.remove(0);
    }

    private boolean isVisited(Location location)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    @Override
    public void setPortals(List<Portal> portals, CollisionChecker portalCollisions) {
        return;
    }
}