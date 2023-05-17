package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;

public class MoveValidator {
    private Actor pacman;
    private final int horzCells;
    private final int vertCells;

    public MoveValidator (Actor pacman, int horzCells, int vertCells) {
        this.pacman = pacman;
        this.horzCells = horzCells;
        this.vertCells =vertCells;
    }

    public boolean canMove(Location location) {
        Color c = pacman.getBackground().getColor(location);
        return !c.equals(Color.gray) && location.getX() < horzCells
                && location.getX() >= 0 && location.getY() < vertCells && location.getY() >= 0;
    }

    public int maxX() {
        return horzCells - 1;
    }

    public int maxY() {
        return vertCells - 1;
    }
}
