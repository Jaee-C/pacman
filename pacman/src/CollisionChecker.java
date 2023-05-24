package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class CollisionChecker {
    private final int horzCells;
    private final int vertCells;
    private List<Location> collisionLocations;

    public CollisionChecker(int horzCells, int vertCells) {
        this.horzCells = horzCells;
        this.vertCells =vertCells;
    }

    public boolean collide(Location location) {
        // Entity out of bounds
        if (location.getX() < 0 || location.getX() >= horzCells ||
                location.getY() < 0 || location.getY() >= vertCells) {
            return true;
        }

        // Entity in wall
        return collisionLocations.contains(location);
    }

    public void setCollisionLocations(List<Location> collisionLocations) {
        this.collisionLocations = collisionLocations;
    }
}
