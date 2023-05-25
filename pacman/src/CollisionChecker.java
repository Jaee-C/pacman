package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class CollisionChecker {
    private List<Location> collisionLocations;

    public CollisionChecker() {
        this.collisionLocations = new ArrayList<>();
    }

    public CollisionChecker(List<Location> collisions) {
        this.collisionLocations = collisions;
    }

    public boolean collide(Location location) {
        // Entity collided
        return collisionLocations.contains(location);
    }

    public void setCollisionLocations(List<Location> collisionLocations) {
        this.collisionLocations = collisionLocations;
    }
}
