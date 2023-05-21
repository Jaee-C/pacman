package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public interface IMover {
    Location move(Actor movingActor, ArrayList<Location> items);
    void setMoveValidator(MoveValidator moveValidator);
}
