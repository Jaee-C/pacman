package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BFS {
    private ArrayList<SearchGameState> queue = new ArrayList<>();

    public List<Location> search(SearchGameState startState) {
        queue.add(startState);
        while (!queue.isEmpty()) {
            SearchGameState currentState = queue.remove(0);
            if (currentState.isGoalState()) {
                System.out.println("Reached goal " + currentState.getCurrentLocation());
                queue = new ArrayList<>();
                return currentState.getPath();
            }
            queue.addAll(currentState.getNextStates());
        }
        return null;
    }
}
