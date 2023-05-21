package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BFS {
    private ArrayList<SearchGameState> queue = new ArrayList<>();
    private HashSet<Location> visited = new HashSet<>();

    public List<Move> search(SearchGameState startState) {
        queue.add(startState);
        while (!queue.isEmpty()) {
            SearchGameState currentState = queue.remove(0);
            if (currentState.isGoalState()) {
                return currentState.getMoves();
            }
            if (!visited.contains(currentState.getPacmanLocation())) {
                visited.add(currentState.getPacmanLocation());
                queue.addAll(currentState.getNextStates());
            }
        }
        return null;
    }
}
