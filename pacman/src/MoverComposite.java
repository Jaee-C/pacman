package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.Arrays;
import java.util.Properties;

public class MoverComposite implements IMover {

    private RandomMover randomMover = new RandomMover();
    private PropertyMover propertyMover = new PropertyMover();
    private IMover mover = null;

    @Override
    public Location move(Actor movingActor, Location closestPill) {
        decideMover();
        return mover.move(movingActor, closestPill);
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        randomMover.setMoveValidator(moveValidator);
        propertyMover.setMoveValidator(moveValidator);
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
            mover = randomMover;
        }
    }
}
