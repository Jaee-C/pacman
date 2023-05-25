package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class Autoplayer {
    private MoverContext mover;
    private Actor movingActor;
    private Game game;

    public Autoplayer(Actor actor, Game game, CollisionChecker wallCollisions) {
        this.movingActor = actor;
        this.game = game;
        this.mover = new MoverContext(wallCollisions);
    }

    public void setPortals(List<Portal> portals, CollisionChecker portalCollisions) {
        mover.setPortals(portals, portalCollisions);
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
