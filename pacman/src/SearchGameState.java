package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;

public class SearchGameState {
    private static int nodes = 0;
    private Location pacmanLocation;
    private Location target;
    private ArrayList<Move> moves;
    private MoveValidator validator;

    public SearchGameState(Location pacmanLocation, Location target, MoveValidator validator, ArrayList<Move> moves, Move next) {
        this.pacmanLocation = pacmanLocation;
        this.target = target;
        this.validator = validator;
        this.moves = new ArrayList<>(moves);
        moves.add(next);
    }

    public ArrayList<SearchGameState> getNextStates() {
        ArrayList<SearchGameState> nextStates = new ArrayList<>();

        for (Location next : pacmanLocation.getNeighbourLocations(0.5)) {
            if (validator.canMove(next)) {
                nextStates.add(new SearchGameState(next, target, validator, moves, new Move(next)));
            }
        }
        System.out.println("Nodes: " + ++nodes + pacmanLocation);

        return nextStates;
    }

    public boolean isGoalState() {
        return pacmanLocation.equals(target);
    }

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public Location getPacmanLocation() {
        return pacmanLocation;
    }
}
