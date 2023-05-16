package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

public interface IMover {
    Location move(Actor movingActor, Location closestPill);
    void setMoveValidator(MoveValidator moveValidator);
}
