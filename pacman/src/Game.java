// PacMan.java
// Simple PacMan implementation
package src;

import ch.aplu.jgamegrid.*;
import src.utility.GameCallback;

import java.awt.*;
import java.util.*;
import java.util.List;

import static ch.aplu.util.QuitPane.dispose;

public class Game extends GameGrid
{
  public final static int nbHorzCells = 20;
  public final static int nbVertCells = 11;
  protected PacManGameGrid grid;

  protected PacActor pacActor;
  private Monster troll;
  private Monster tx5;

  private ArrayList<Location> pillAndItemLocations = new ArrayList<>();
  private ArrayList<Actor> iceCubes = new ArrayList<>();
  private ArrayList<Actor> goldPieces = new ArrayList<>();
  private PortalStore portals = new PortalStore();
  private GameCallback gameCallback;
  private int seed = 30006;
  private List<Location> wallLocations = new ArrayList<>();
  public boolean lose = false;

  public Game(GameCallback gameCallback, Properties properties, PacManGameGrid level)
  {
    //Setup game

    super(nbHorzCells, nbVertCells, 20, false);

    this.gameCallback = gameCallback;

    this.grid = level;

    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");


    GGBackground bg = getBg();
    drawGrid(bg);

    // Setup actors
    this.troll = new Monster(this, MonsterType.Troll, portals, wallLocations);
    this.tx5 = new Monster(this, MonsterType.TX5, portals, wallLocations);
    this.pacActor = new PacActor(this, portals, wallLocations);

    //Setup for auto test
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    pacActor.setSeed(seed);
    troll.setSeed(seed);
    tx5.setSeed(seed);
    addKeyRepeatListener(pacActor);
    setKeyRepeatPeriod(150);
    troll.setSlowDown(3);
    tx5.setSlowDown(3);
    pacActor.setSlowDown(3);
    tx5.stopMoving(5);

    setupActorLocations();
    setupPillAndItemsLocations();

    //Run the game
    doRun();
    show();
  }

  @Override
  public void act() {
    super.act();
    isEnd();
  }

  public String isEnd() {
    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    String returnVal = "";

    int maxPillsAndItems = countPillsAndItems();

    if (pacActor == null || troll == null || tx5 == null || !this.getFrame().isActive()) return "";

    boolean hasPacmanBeenHit = false;
    if (this.getActors().containsAll(Arrays.asList(troll, tx5))) {
      hasPacmanBeenHit = troll.getLocation().equals(pacActor.getLocation()) ||
              tx5.getLocation().equals(pacActor.getLocation());
    }

    boolean hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
    if (hasPacmanBeenHit || hasPacmanEatAllPills) {
      Location loc = pacActor.getLocation();
      troll.setStopMoving(true);
      tx5.setStopMoving(true);
      pacActor.removeSelf();

      String title = "";

      GGBackground bg = getBg();
      if (hasPacmanBeenHit) {
        bg.setPaintColor(Color.red);
        title = "GAME OVER";
        addActor(new Actor("sprites/explosion3.gif"), loc);
        setTitle(title);
        gameCallback.endOfGame(title);
        doPause();

        lose = true;

        Driver driver = Driver.getInstance();
        driver.setMode(Driver.DriverMode.EDIT);

        returnVal = "Pacman Hit";
      } else {
        bg.setPaintColor(Color.yellow);
        title = "YOU WIN";
        setTitle(title);
        gameCallback.endOfGame(title);
        doPause();

        Driver driver = Driver.getInstance();
        driver.nextLevel();

        returnVal = "Win";
      }
    }

    return returnVal;
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }

  private void setupActorLocations() {
    if(!grid.getTX5Locations().isEmpty()){
      addActor(tx5, grid.getTX5Locations().get(0), Location.NORTH);
    }

    if(!grid.getTrollLocations().isEmpty()){
      addActor(troll, grid.getTrollLocations().get(0), Location.NORTH);
    }

    addActor(pacActor, grid.getPacManStartLocation());

  }

  private int countPillsAndItems() {
    int pillsAndItemsCount = 0;
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        GameGridCell a = grid.getCell(location);

        if (a == GameGridCell.Gold) { // Gold
          pillsAndItemsCount++;
        } else if (a == GameGridCell.Pill){
          pillsAndItemsCount++;
        }
      }
    }

    return pillsAndItemsCount;
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return pillAndItemLocations;
  }

  public void removePillAndItemLocation(Location location) {
    pillAndItemLocations.remove(location);
  }

  private void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        GameGridCell a = grid.getCell(location);
        if (a == GameGridCell.Pill) {
          pillAndItemLocations.add(location);
        }
        if (a == GameGridCell.Gold) {
          pillAndItemLocations.add(location);
        }
        if (a == GameGridCell.Ice) {
          pillAndItemLocations.add(location);
        }
      }
    }
  }

  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        GameGridCell a = grid.getCell(location);

        if (a != GameGridCell.Wall) { // Path
          bg.fillCell(location, Color.lightGray);
        } else {
          wallLocations.add(location); // Wall
        }
        if (a == GameGridCell.Pill ) { // Pill
          putPill(bg, location);
        } else if (a == GameGridCell.Gold ) { // Gold
          putGold(bg, location);
        } else if (a == GameGridCell.Ice) {
          putIce(bg, location);
        } else if (a == GameGridCell.Portal_Dark_Gold) {
          this.portals.put(this, PortalColour.DARK_GOLD, location);
        } else if (a == GameGridCell.Portal_Dark_Grey) {
          this.portals.put(this, PortalColour.DARK_GREY, location);
        } else if (a == GameGridCell.Portal_Yellow) {
          this.portals.put(this, PortalColour.YELLOW, location);
        } else if (a == GameGridCell.Portal_White) {
          this.portals.put(this, PortalColour.WHITE, location);
        }
      }
    }
  }

  private void putPill(GGBackground bg, Location location){
    bg.fillCircle(toPoint(location), 5);
  }

  private void putGold(GGBackground bg, Location location){
    bg.setPaintColor(Color.yellow);
    bg.fillCircle(toPoint(location), 5);
    Actor gold = new Actor("sprites/gold.png");
    this.goldPieces.add(gold);
    addActor(gold, location);
  }

  private void putIce(GGBackground bg, Location location){
    bg.setPaintColor(Color.blue);
    bg.fillCircle(toPoint(location), 5);
    Actor ice = new Actor("sprites/ice.png");
    this.iceCubes.add(ice);
    addActor(ice, location);
  }

  public void removeItem(String type,Location location){
    if(type.equals("gold")){
      for (Actor item : this.goldPieces){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }else if(type.equals("ice")){
      for (Actor item : this.iceCubes){
        if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
          item.hide();
        }
      }
    }
  }

  public int getNumHorzCells(){
    return this.nbHorzCells;
  }
  public int getNumVertCells(){
    return this.nbVertCells;
  }

  public void close() {
    System.out.println("Close Game");
    stopGameThread();
//    this.getFrame().dispose();
    this.hide();
  }
}
