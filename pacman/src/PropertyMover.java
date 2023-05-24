package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PropertyMover implements IMover {
    private List<String> propertyMoves = new ArrayList<>();
    private int propertyMoveIndex = 0;
    private CollisionChecker collisionChecker;

    @Override
    public Location move(Actor movingActor, Location target) {
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

        if (next != null && !collisionChecker.collide(next))
            return next;

        return null;
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    public boolean usePropertyMover() {
        return propertyMoveIndex < propertyMoves.size();
    }

    public void setPropertyMoves(String propertyMoveString) {
        if (propertyMoveString != null) {
            this.propertyMoves = Arrays.asList(propertyMoveString.split(","));
        }
    }

    @Override
    public void setPortals(List<Portal> portals, CollisionChecker portalCollisions) {
        return;
    }
}
