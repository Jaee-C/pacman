// PacActor.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.*;

public class PacActor extends Actor implements GGKeyRepeatListener
{
  private static final int nbSprites = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;
  private Game game;
  private int seed;
  private Random randomiser = new Random();
  private Autoplayer autoplayer;
  private CollisionChecker wallCollisions;
  private CollisionChecker portalCollisions;
  private List<Portal> portals;
  public PacActor(Game game)
  {
    super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
    this.game = game;
    this.wallCollisions = new CollisionChecker(game.getNumHorzCells(), game.getNumVertCells());
    this.portalCollisions = new CollisionChecker(game.getNumHorzCells(), game.getNumVertCells());
    this.autoplayer = new Autoplayer(this, game, wallCollisions);
  }
  private boolean isAuto = false;

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public void setupPortals(List<Portal> portals) {
    List<Location> portalLocations = new ArrayList<>();
    for (Portal portal: portals) {
      portalLocations.add(portal.getLocation());
    }
    this.portals = portals;
    this.portalCollisions.setCollisionLocations(portalLocations);
  }

  public void setupWalls(List<Location> wallLocations) {
    this.wallCollisions.setCollisionLocations(wallLocations);
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setPropertyMoves(String propertyMoveString) {
    autoplayer.setPropertyMoves(propertyMoveString);
  }

  public void keyRepeated(int keyCode)
  {
    if (isAuto) {
      return;
    }
    if (isRemoved())  // Already removed
      return;
    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
        break;
    }
    if (next != null && !wallCollisions.collide(next))
    {
      setLocation(next);
      eatPill(next);
    }

    if (next != null && portalCollisions.collide(next))
    {
      for (Portal portal: portals) {
        if (portal.getLocation().equals(next)) {
          System.out.println("Portal: reached");
          portal.teleport(this);
        }
      }
    }
  }

  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;

    if (isAuto) {
      Location next = autoplayer.move(game.getPillAndItemLocations());
      if (next != null) {
        // Move action is not a turn action
        setLocation(next);
        eatPill(next);
      }
    }
    this.game.getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }

  public int getNbPills() {
    return nbPills;
  }

  private void eatPill(Location location)
  {
    // Pacman collisions
    Color c = getBackground().getColor(location);
    if (c.equals(Color.white))
    {
      nbPills++;
      score++;
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "pills");
    } else if (c.equals(Color.yellow)) {
      nbPills++;
      score+= 5;
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "gold");
      game.removeItem("gold",location);
    } else if (c.equals(Color.blue)) {
      getBackground().fillCell(location, Color.lightGray);
      game.getGameCallback().pacManEatPillsAndItems(location, "ice");
      game.removeItem("ice",location);
    }
    game.removePillAndItemLocation(location);
    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }


}
