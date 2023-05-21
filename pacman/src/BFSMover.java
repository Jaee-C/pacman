package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.List;

public class BFSMover implements IMover {
    private BFS searcher = new BFS();
    private MoveValidator moveValidator;
    private List<Move> path = new ArrayList<>();

    @Override
    public Location move(Actor movingActor, ArrayList<Location> items) {
        if (path.isEmpty() && !items.isEmpty()) {
            Location nextItem = items.get(0);
            SearchGameState gameState = new SearchGameState(movingActor.getLocation(), nextItem, moveValidator, new ArrayList<>(), new Move(movingActor.getLocation()));
            path = searcher.search(gameState);
        }

        if (path.isEmpty()) {
            return movingActor.getLocation();
        }
        Move nextMove = path.remove(0);
        return nextMove.getLocation();
    }

    @Override
    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }
}
