package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class MoveValidator {
    private Actor pacman;
    private final int horzCells;
    private final int vertCells;
    private List<Location> collisionLocations;

    public MoveValidator (Actor pacman, int horzCells, int vertCells) {
        this.pacman = pacman;
        this.horzCells = horzCells;
        this.vertCells =vertCells;
    }

    public boolean canMove(Location location) {
        // Entity out of bounds
        if (location.getX() < 0 || location.getX() >= horzCells ||
                location.getY() < 0 || location.getY() >= vertCells) {
            return false;
        }

        // Entity in wall
        return !collisionLocations.contains(location);
    }

    public void setCollisionLocations(List<Location> collisionLocations) {
        this.collisionLocations = collisionLocations;
    }
}
