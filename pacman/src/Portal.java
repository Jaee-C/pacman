package src;

import ch.aplu.jgamegrid.Actor;



public class Portal extends Actor {
    private Game game;
    private PortalColour colour;
    public Portal(Game game, PortalColour colour) {
        super("sprites/" + colour.getImageName());
        this.game = game;
        this.colour = colour;
    }

    public PortalColour getColour() {
        return colour;
    }

    public Portal getPortalPartner() {
        for (Portal p : game.getPortals()) {
            if (p.getColour() == this.colour && p != this) {
                return p;
            }
        }
        return null;
    }

    public void teleport(Actor actor) {
        // called when actor collides with portal and actor's previous location was not the portal location

        // teleports actor to portal partner
        actor.setPixelLocation(this.getPortalPartner().getPixelLocation());
    }
}
