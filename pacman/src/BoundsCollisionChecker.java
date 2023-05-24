package src;

import ch.aplu.jgamegrid.Location;

public class BoundsCollisionChecker extends CollisionChecker {

    public BoundsCollisionChecker(int horzCells, int vertCells) {
        super(horzCells, vertCells);
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
