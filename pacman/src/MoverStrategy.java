package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.List;

public class MoverStrategy implements IMover {

    private RandomMover randomMover = new RandomMover();
    private BFSMover bfsMover = new BFSMover();
    private IMover mover = null;

    @Override
    public Location move(Actor movingActor, Location target) {
        Location next = null;
        mover = bfsMover;
        if ((next = mover.move(movingActor, target)) == null) {
            mover = randomMover;
            next = mover.move(movingActor, target);
        }
    
        return next;
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        randomMover.setCollisionChecker(collisionChecker);
        bfsMover.setCollisionChecker(collisionChecker);
    }

    @Override
    public void setPortals(List<Portal> portals, CollisionChecker portalCollisions) {
        randomMover.setPortals(portals, portalCollisions);
        bfsMover.setPortals(portals, portalCollisions);
    }

    public void setSeed(int seed) {
        randomMover.setSeed(seed);
    }
}
