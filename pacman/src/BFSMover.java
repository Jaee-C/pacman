package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class BFSMover implements IMover {
    private BFS searcher = new BFS();
    private CollisionChecker collisionChecker;
    private List<Location> path = new ArrayList<>();

    @Override
    public Location move(Actor movingActor, Location target) {
        if (path.isEmpty()) {
            Location nextItem = target;
            System.out.println("Next: " + nextItem);
            System.out.println("Initial Location: " + movingActor.getLocation());
            SearchGameState gameState = new SearchGameState(movingActor.getLocation(), nextItem, collisionChecker, new ArrayList<>());
            path = searcher.search(gameState);
        }

        if (path.isEmpty()) {
            return movingActor.getLocation();
        }
        Location next = path.remove(0);
        return next;
    }

    @Override
    public void setMoveValidator(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }
}
