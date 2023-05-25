package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class Autoplayer {
    private MoverContext mover;
    private Actor movingActor;

    public Autoplayer(Actor actor, CollisionChecker wallCollisions) {
        this.movingActor = actor;
        this.mover = new MoverContext(wallCollisions);
    }

    public void setPortals(PortalStore portals) {
        mover.setPortals(portals);
    }

    public Location move(ArrayList<Location> items) {
        if (movingActor == null) {
            throw new RuntimeException("No actor registered for autoplayer");
        }
        Location closestPillLocation = closestPillLocation(movingActor, items);
        return mover.move(movingActor, closestPillLocation);
    }

    public void setSeed(int seed) {
        mover.setSeed(seed);
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
