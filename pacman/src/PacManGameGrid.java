// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

import java.util.ArrayList;

public class PacManGameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private GameGridCell[][] mazeArray;

  public PacManGameGrid(int nbHorzCells, int nbVertCells)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new GameGridCell[nbVertCells][nbHorzCells];
    String maze =
      "xxxxxxxxxxxxxxxxxxxx" + // 0
      "x....x....g...x....x" + // 1
      "xgxx.x.xxxxxx.x.xx.x" + // 2
      "x.x.......i.g....x.x" + // 3
      "x.x.xx.xx  xx.xx.x.x" + // 4
      "x......x    x......x" + // 5
      "x.x.xx.xxxxxx.xx.x.x" + // 6
      "x.x......gi......x.x" + // 7
      "xixx.x.xxxxxx.x.xx.x" + // 8
      "x...gx....g...x....x" + // 9
      "xxxxxxxxxxxxxxxxxxxx";// 10


    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        GameGridCell value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }
  public PacManGameGrid(int nbHorzCells, int nbVertCells, String maze)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    mazeArray = new GameGridCell[nbVertCells][nbHorzCells];

    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        GameGridCell value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  public Location getPacManStartLocation()
  {
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        if (mazeArray[i][k] == GameGridCell.Pacman)
          return new Location(k, i);
      }
    }
    return null;
  }

  public ArrayList<Location> getTrollLocations(){
    ArrayList<Location> trollLocations = new ArrayList<>();
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        if (mazeArray[i][k] == GameGridCell.Troll)
          trollLocations.add(new Location(k,i));
      }
    }
    return trollLocations;

  }

  public ArrayList<Location> getTX5Locations(){
    ArrayList<Location> TX5Locations = new ArrayList<>();
    for (int i = 0; i < nbVertCells; i++)
    {
      for (int k = 0; k < nbHorzCells; k++) {
        if (mazeArray[i][k] == GameGridCell.TX5)
          TX5Locations.add(new Location(k,i));
      }
    }
    return TX5Locations;

  }

  public GameGridCell getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private GameGridCell toInt(char c)
  {
    if (c == 'x')
      return GameGridCell.Wall;
    if (c == '.')
      return GameGridCell.Pill;
    if (c == ' ')
      return GameGridCell.Path;
    if (c == 'g')
      return GameGridCell.Gold;
    if (c == 'i')
      return GameGridCell.Ice;
    if (c == 'p')
        return GameGridCell.Pacman;
    if (c == 't')
        return GameGridCell.Troll;
    if (c == '5')
        return GameGridCell.TX5;
    if (c == 'w')
        return GameGridCell.Portal_White;
    if (c == 'y')
        return GameGridCell.Portal_Yellow;
    if (c == 'o')
        return GameGridCell.Portal_Dark_Gold;
    if (c == 'a')
        return GameGridCell.Portal_Dark_Grey;
    return GameGridCell.INVALID;
  }

  public int getNbHorzCells() {
    return nbHorzCells;
  }

  public int getNbVertCells() {
    return nbVertCells;
  }
}
