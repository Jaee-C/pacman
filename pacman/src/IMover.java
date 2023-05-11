package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public interface IMover {
    public Location move(Actor movingActor, Location closestPill);
    public void setMoveValidator(MoveValidator moveValidator);
}
