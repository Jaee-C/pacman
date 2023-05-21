package src;

import ch.aplu.jgamegrid.Location;

public class Move {
    private Location location;
    public Move(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
