// Monster.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.*;

public class Monster extends Actor
{
  private Game game;
  private MonsterType type;
  private ArrayList<Location> visitedList = new ArrayList<Location>();
  private final int listLength = 10;
  private boolean stopMoving = false;
  private int seed = 0;
  private Random randomiser = new Random(0);
  private CollisionChecker wallCollisions;
  private CollisionChecker portalCollisions;
  private PortalStore portals;


  public Monster(Game game, MonsterType type)
  {
    super("sprites/" + type.getImageName());
    this.game = game;
    this.type = type;
    this.wallCollisions = new BoundsCollisionChecker(game.getNumHorzCells(), game.getNumVertCells());
    this.portalCollisions = new CollisionChecker(game.getNumHorzCells(), game.getNumVertCells());
  }

  public void stopMoving(int seconds) {
    this.stopMoving = true;
    Timer timer = new Timer(); // Instantiate Timer Object
    int SECOND_TO_MILLISECONDS = 1000;
    final Monster monster = this;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
      }
    }, seconds * SECOND_TO_MILLISECONDS);
  }

  public void setupPortals(PortalStore portals) {
    this.portals = portals;
    this.portalCollisions.setCollisionLocations(portals.getLocations());
  }

  public void setupWalls(List<Location> wallLocations) {
    this.wallCollisions.setCollisionLocations(wallLocations);
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

  public void act()
  {
    if (stopMoving) {
      return;
    }
    walkApproach();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  private void walkApproach()
  {
    Location pacLocation = game.pacActor.getLocation();
    double oldDirection = getDirection();

    // Walking approach:
    // TX5: Determine direction to pacActor and try to move in that direction. Otherwise, random walk.
    // Troll: Random walk.
    Location.CompassDirection compassDir =
      getLocation().get4CompassDirectionTo(pacLocation);
    Location next = getLocation().getNeighbourLocation(compassDir);
    setDirection(compassDir);
    if (type == MonsterType.TX5 &&
      !isVisited(next) && !wallCollisions.collide(next))
    {
      setLocation(next);
    }
    else
    {
      // Random walk
      int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
      setDirection(oldDirection);
      turn(sign * 90);  // Try to turn left/right
      next = getNextMoveLocation();
      if (!wallCollisions.collide(next))
      {
        setLocation(next);
      }
      else
      {
        setDirection(oldDirection);
        next = getNextMoveLocation();
        if (!wallCollisions.collide(next)) // Try to move forward
        {
          setLocation(next);
        }
        else
        {
          setDirection(oldDirection);
          turn(-sign * 90);  // Try to turn right/left
          next = getNextMoveLocation();
          if (!wallCollisions.collide(next))
          {
            setLocation(next);
          }
          else
          {

            setDirection(oldDirection);
            turn(180);  // Turn backward
            next = getNextMoveLocation();
            setLocation(next);
          }
        }
      }
    }
    game.getGameCallback().monsterLocationChanged(this);
    addVisitedList(next);

    if (portalCollisions.collide(next)) {
      Portal portal = portals.getPortalAt(next);
      if (portal != null) {
        portal.teleport(portals, this);
      }
    }
  }

  public MonsterType getType() {
    return type;
  }

  private void addVisitedList(Location location)
  {
    visitedList.add(location);
    if (visitedList.size() == listLength)
      visitedList.remove(0);
  }

  private boolean isVisited(Location location)
  {
    for (Location loc : visitedList)
      if (loc.equals(location))
        return true;
    return false;
  }
}
