package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public class Autoplayer {
    private MoverStrategy mover = new MoverStrategy();
    private Actor movingActor;

    public Autoplayer(Actor actor) {
        this.movingActor = actor;
        MoveValidator moveValidator = new MoveValidator(actor, Game.nbHorzCells, Game.nbVertCells);
        mover.setMoveValidator(moveValidator);
    }

    public void setPropertyMoves(String propertyMoveString) {
        mover.setPropertyMoves(propertyMoveString);
    }

    public Location move(Location closestPill) {
        if (movingActor == null) {
            throw new RuntimeException("No actor registered for autoplayer");
        }
        return mover.move(movingActor, closestPill);
    }

    public void setSeed(int seed) {
        mover.setSeed(seed);
    }


}
