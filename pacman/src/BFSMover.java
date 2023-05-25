package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class BFSMover implements IMoverStrategy {
    private BFS searcher = new BFS();
    private CollisionChecker collisionChecker;
    private List<Location> path = new ArrayList<>();
    private PortalStore portals;

    @Override
    public Location move(Actor movingActor, Location target) {
        if (path.isEmpty()) {
            Location nextItem = target;
            SearchGameState gameState = new SearchGameState(movingActor.getLocation(), nextItem, collisionChecker, new ArrayList<>(), portals);
            path = searcher.search(gameState);
        }

        if (path.isEmpty()) {
            return movingActor.getLocation();
        }
        Location next = path.remove(0);
        return next;
    }

    @Override
    public void setCollisionChecker(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    @Override
    public void setPortals(PortalStore portals) {
        this.portals = portals;
    }
}
