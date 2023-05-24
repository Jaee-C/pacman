package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class CollisionChecker {
    protected final int horzCells;
    protected final int vertCells;
    private List<Location> collisionLocations;

    public CollisionChecker(int horzCells, int vertCells) {
        this.horzCells = horzCells;
        this.vertCells =vertCells;
    }

    public boolean collide(Location location) {
        // Entity collided
        return collisionLocations.contains(location);
    }

    public void setCollisionLocations(List<Location> collisionLocations) {
        this.collisionLocations = collisionLocations;
    }
}
