package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyMover implements IMover {
    private List<String> propertyMoves = new ArrayList<>();
    private int propertyMoveIndex = 0;
    private MoveValidator moveValidator;

    public Location move(Actor movingActor, Location closestPill) {
        String currentMove = propertyMoves.get(propertyMoveIndex);
        Location next = null;
        propertyMoveIndex++;
        switch(currentMove) {
            case "R":
                movingActor.turn(90);
                break;
            case "L":
                movingActor.turn(-90);
                break;
            case "M":
                next = movingActor.getNextMoveLocation();
                break;
        }

        if (next != null && moveValidator.canMove(next))
            return next;

        return null;
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    public boolean usePropertyMover() {
        return propertyMoveIndex < propertyMoves.size();
    }

    public void setPropertyMoves(String propertyMoveString) {
        if (propertyMoveString != null) {
            this.propertyMoves = Arrays.asList(propertyMoveString.split(","));
        }
    }
}
