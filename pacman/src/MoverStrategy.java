package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class MoverStrategy implements IMover {

    private RandomMover randomMover = new RandomMover();
    private PropertyMover propertyMover = new PropertyMover();
    private ClosestPillMover closestPillMover = new ClosestPillMover();
    private BFSMover bfsMover = new BFSMover();
    private IMover mover = null;

    @Override
    public Location move(Actor movingActor, Location target) {
        Location next = null;

        if (propertyMover.usePropertyMover()) {
            return propertyMover.move(movingActor, target);
        } else {
            mover = bfsMover;
        }

        if ((next = mover.move(movingActor, target)) == null) {
            mover = randomMover;
            next = mover.move(movingActor, target);
        }
    
        return next;
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        randomMover.setMoveValidator(moveValidator);
        propertyMover.setMoveValidator(moveValidator);
        closestPillMover.setMoveValidator(moveValidator);
        bfsMover.setMoveValidator(moveValidator);
    }

    public void setPropertyMoves(String propertyMoveString) {
        propertyMover.setPropertyMoves(propertyMoveString);
    }

    public void setSeed(int seed) {
        randomMover.setSeed(seed);
    }
}
