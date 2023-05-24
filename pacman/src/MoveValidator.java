package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MoveValidator {
    private Actor pacman;
    private final int horzCells;
    private final int vertCells;
    private List<Location> invalidLocations;

    public MoveValidator (Actor pacman, int horzCells, int vertCells) {
        this.pacman = pacman;
        this.horzCells = horzCells;
        this.vertCells =vertCells;
    }

    public boolean canMove(Location location) {
        // Entity out of bounds
        if (location.getX() <= 0 || location.getX() >= horzCells ||
                location.getY() <= 0 || location.getY() >= vertCells) {
            return false;
        }

        // Entity in wall
        return !invalidLocations.contains(location);
    }

    public void setInvalidLocations(List<Location> invalidLocations) {
        this.invalidLocations = invalidLocations;
    }

    public int maxX() {
        return horzCells - 1;
    }

    public int maxY() {
        return vertCells - 1;
    }
}
