package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public interface IMoverStrategy {
    Location move(Actor movingActor, Location target);
    void setPortals(PortalStore portals);
}
