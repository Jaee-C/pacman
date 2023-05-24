package src;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class PortalStore {
    private List<Portal> portals = new ArrayList<>();
    private PortalFactory factory = PortalFactory.getInstance();

    public void put(GameGrid game, PortalColour colour, Location location) {
        Portal portal = factory.createPortal(colour);
        game.addActor(portal, location);
        portals.add(portal);
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();
        for (Portal p : portals) {
            locations.add(p.getLocation());
        }
        return locations;
    }

    public List<Portal> getAll() {
        return portals;
    }

    public Portal getPortalAt(Location location) {
        for (Portal p : portals) {
            if (p.getLocation().equals(location)) {
                return p;
            }
        }
        return null;
    }
}
