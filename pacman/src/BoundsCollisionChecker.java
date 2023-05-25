package src;

import ch.aplu.jgamegrid.Location;

import java.util.List;

public class BoundsCollisionChecker extends CollisionChecker {
    private final int horzCells;
    private final int vertCells;

    public BoundsCollisionChecker(int horzCells, int vertCells) {
        super();
        this.horzCells = horzCells;
        this.vertCells = vertCells;
    }

    public BoundsCollisionChecker(int horzCells, int vertCells, List<Location> collisions) {
        super(collisions);
        this.horzCells = horzCells;
        this.vertCells = vertCells;
    }

    @Override
    public boolean collide(Location location) {
        // Entity out of bounds
        if (location.getX() < 0 || location.getX() >= horzCells ||
                location.getY() < 0 || location.getY() >= vertCells) {
            return true;
        }

        return super.collide(location);
    }
}
