package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class BFSMover implements IMoverStrategy {
    private CollisionChecker collisionChecker;
    private List<Location> path = new ArrayList<>();
    private PortalStore portals;

    public BFSMover(CollisionChecker collisionChecker) {
        this.collisionChecker = collisionChecker;
    }

    @Override
    public Location move(Actor movingActor, Location target) {
        if (path.isEmpty()) {
            Location nextItem = target;
            SearchGameState gameState = new SearchGameState(movingActor.getLocation(), nextItem, collisionChecker, new ArrayList<>(), portals);
            path = search(gameState);
        }

        if (path.isEmpty()) {
            return movingActor.getLocation();
        }
        Location next = path.remove(0);
        return next;
    }

    @Override
    public void setPortals(PortalStore portals) {
        this.portals = portals;
    }

    private List<Location> search(SearchGameState startState) {
        ArrayList<SearchGameState> queue = new ArrayList<>();
        queue.add(startState);
        while (!queue.isEmpty()) {
            SearchGameState currentState = queue.remove(0);
            if (currentState.isGoalState()) {
                queue = new ArrayList<>();
                return currentState.getPath();
            }
            queue.addAll(currentState.getNextStates());
        }
        return null;
    }
}
