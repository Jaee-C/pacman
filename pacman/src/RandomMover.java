package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Random;

public class RandomMover implements IMover {
    private int seed;
    private Random randomiser = new Random();
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private final int listLength = 10;
    private MoveValidator moveValidator = null;

    @Override
    public Location move(Actor movingActor, Location closestPill) {
        if (this.moveValidator == null) {
            throw new IllegalStateException("MoveValidator is not set");
        }
        double oldDirection = movingActor.getDirection();

        Location.CompassDirection compassDir =
                movingActor.getLocation().get4CompassDirectionTo(closestPill);
        Location next = movingActor.getLocation().getNeighbourLocation(compassDir);
        movingActor.setDirection(compassDir);
        if (!isVisited(next) && moveValidator.canMove(next)) {
            addVisitedList(next);
            return next;
        } else {
            // normal movement
            int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
            movingActor.setDirection(oldDirection);
            movingActor.turn(sign * 90);  // Try to turn left/right
            next = movingActor.getNextMoveLocation();
            if (moveValidator.canMove(next)) {
                addVisitedList(next);
                return next;
            } else {
                movingActor.setDirection(oldDirection);
                next = movingActor.getNextMoveLocation();
                if (moveValidator.canMove(next)) // Try to move forward
                {
                    addVisitedList(next);
                    return next;
                } else {
                    movingActor.setDirection(oldDirection);
                    movingActor.turn(-sign * 90);  // Try to turn right/left
                    next = movingActor.getNextMoveLocation();
                    if (moveValidator.canMove(next)) {
                        addVisitedList(next);
                        return next;
                    } else {
                        movingActor.setDirection(oldDirection);
                        movingActor.turn(180);  // Turn backward
                        next = movingActor.getNextMoveLocation();
                        addVisitedList(next);
                        return next;
                    }
                }
            }
        }
    }

    public void setSeed(int seed) {
        this.seed = seed;
        randomiser.setSeed(seed);
    }

    private void addVisitedList(Location location)
    {
        visitedList.add(location);
        if (visitedList.size() == listLength)
            visitedList.remove(0);
    }

    private boolean isVisited(Location location)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }
}
