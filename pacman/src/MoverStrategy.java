package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class MoverStrategy implements IMover {

    private RandomMover randomMover = new RandomMover();
    private PropertyMover propertyMover = new PropertyMover();
    private ClosestPillMover closestPillMover = new ClosestPillMover();
    private IMover mover = null;

    @Override
    public Location move(Actor movingActor, Location closestPill) {
        decideMover();
        Location next = null;

        if ((next = mover.move(movingActor, closestPill)) == null) {
            mover = randomMover;
            next = mover.move(movingActor, closestPill);
        }
    
        return next;
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        randomMover.setMoveValidator(moveValidator);
        propertyMover.setMoveValidator(moveValidator);
        closestPillMover.setMoveValidator(moveValidator);
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
