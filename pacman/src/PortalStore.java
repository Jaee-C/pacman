package src;

import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class PortalStore {
    private List<Portal> portals = new ArrayList<>();
    private PortalFactory factory = PortalFactory.getInstance();
    private CollisionChecker collision = new CollisionChecker();

    public void put(GameGrid game, PortalColour colour, Location location) {
        Portal portal = factory.createPortal(colour);
        game.addActor(portal, location);
        portals.add(portal);
        collision.addCollisionLocation(location);
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

    /**
     * Checks if location collides with a portal and teleports if so
     * Returns teleported location if teleported, otherwise returns original location
     */
    public Location checkAndTeleport(Location location) {
        if (collision.collide(location)) {
            Portal collided = getPortalAt(location);
            return collided.teleport(this);
        }
        return location;
    }
}
