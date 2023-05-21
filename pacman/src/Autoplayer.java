package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class Autoplayer {
    private MoverStrategy mover = new MoverStrategy();
    private Actor movingActor;
    private Game game;

    public Autoplayer(Actor actor, Game game) {
        this.movingActor = actor;
        this.game = game;
        MoveValidator moveValidator = new MoveValidator(actor, Game.nbHorzCells, Game.nbVertCells);
        mover.setMoveValidator(moveValidator);
    }

    public void setPropertyMoves(String propertyMoveString) {
        mover.setPropertyMoves(propertyMoveString);
    }

    public Location move(ArrayList<Location> items) {
        if (movingActor == null) {
            throw new RuntimeException("No actor registered for autoplayer");
        }
        return mover.move(movingActor, items);
    }

    public void setSeed(int seed) {
        mover.setSeed(seed);
    }

}
