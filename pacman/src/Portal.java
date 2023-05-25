package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;


public class Portal extends Actor {
    private PortalColour colour;
    public Portal(PortalColour colour) {
        super("sprites/" + colour.getImageName());
        this.colour = colour;
    }

    public PortalColour getColour() {
        return colour;
    }

    private Portal getPortalPartner(PortalStore store) {
        for (Portal p : store.getAll()) {
            if (p.getColour() == this.colour && p != this) {
                return p;
            }
        }
        return null;
    }

    /**
     * called when actor collides with portal and actor's previous location was not the portal location
     */
    public Location teleport(PortalStore store) {
        // teleports actor to portal partner
        Portal partner = this.getPortalPartner(store);
        if (partner == null)
            return null;
        return partner.getLocation();
    }
}
