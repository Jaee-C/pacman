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
    public Location move(Actor movingActor, ArrayList<Location> items) {
        decideMover();
        Location next = null;

        if ((next = mover.move(movingActor, items)) == null) {
            mover = bfsMover;
            next = mover.move(movingActor, items);
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

    private void decideMover() {
        if (propertyMover.usePropertyMover()) {
            mover = propertyMover;
        } else {
            mover = closestPillMover;
        }
    }
}
