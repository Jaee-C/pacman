// PacActor.java
// Used for PacMan
package src;

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
  public PacActor(Game game)
  {
    super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
    this.game = game;
    this.autoplayer = new Autoplayer(this, game);
  }
  private boolean isAuto = false;

  public void setAuto(boolean auto) {
    isAuto = auto;
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
    if (next != null && canMove(next))
    {
      setLocation(next);
      eatPill(next);
    }
  }

  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;

    if (isAuto) {
      Location next = autoplayer.move(closestPillLocation());
      if (next != null) {
        // Move action is not a turn action
        setLocation(next);
        eatPill(next);
      }
    }
    this.game.getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }

  private Location closestPillLocation() {
    int currentDistance = 1000;
    Location currentLocation = null;
    List<Location> pillAndItemLocations = game.getPillAndItemLocations();
    for (Location location: pillAndItemLocations) {
      int distanceToPill = location.getDistanceTo(getLocation());
      if (distanceToPill < currentDistance) {
        currentLocation = location;
        currentDistance = distanceToPill;
      }
    }

    return currentLocation;
  }

  private boolean canMove(Location location)
  {
    Color c = getBackground().getColor(location);
    if ( c.equals(Color.gray) || location.getX() >= game.getNumHorzCells()
            || location.getX() < 0 || location.getY() >= game.getNumVertCells() || location.getY() < 0)
      return false;
    else
      return true;
  }

  public int getNbPills() {
    return nbPills;
  }

  private void eatPill(Location location)
  {
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
    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }


}
