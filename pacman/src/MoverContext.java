package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class MoverContext {

    private RandomMover randomMover = new RandomMover();
    private BFSMover bfsMover = new BFSMover();
    private IMoverStrategy mover = null;

    public MoverContext(CollisionChecker collision) {
        randomMover.setCollisionChecker(collision);
        bfsMover.setCollisionChecker(collision);
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

    public void setCollisionChecker(CollisionChecker collisionChecker) {
        randomMover.setCollisionChecker(collisionChecker);
        bfsMover.setCollisionChecker(collisionChecker);
    }

    public void setPortals(PortalStore portals) {
        randomMover.setPortals(portals);
        bfsMover.setPortals(portals);
    }

    public void setSeed(int seed) {
        randomMover.setSeed(seed);
    }
}
