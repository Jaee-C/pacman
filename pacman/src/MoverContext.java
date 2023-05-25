package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class MoverContext {

    private RandomMover randomMover;
    private BFSMover bfsMover;
    private IMoverStrategy mover = null;

    public MoverContext(CollisionChecker collision) {
        this.randomMover = new RandomMover(collision);
        this.bfsMover = new BFSMover(collision);
    }

    public Location move(Actor movingActor, Location target) {
        Location next = null;
        mover = bfsMover;
        if ((next = mover.move(movingActor, target)) == null) {
            mover = randomMover;
            next = mover.move(movingActor, target);
        }
    
        return next;
    }

    public void setPortals(PortalStore portals) {
        randomMover.setPortals(portals);
        bfsMover.setPortals(portals);
    }

    public void setSeed(int seed) {
        randomMover.setSeed(seed);
    }
}
