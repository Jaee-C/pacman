// PacGrid.java
package src;

import ch.aplu.jgamegrid.*;

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
        if (mazeArray[i][k] == GameGridCell.PAC)
          return new Location(k, i);
      }
    }
    return null;
  }

  public GameGridCell getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }
  private GameGridCell toInt(char c)
  {
    if (c == 'x')
      return GameGridCell.WALL;
    if (c == '.')
      return GameGridCell.PILL;
    if (c == ' ')
      return GameGridCell.PATH;
    if (c == 'g')
      return GameGridCell.GOLD;
    if (c == 'i')
      return GameGridCell.ICE;
    if (c == 'p')
        return GameGridCell.PAC;
    if (c == 't')
        return GameGridCell.TROLL;
    if (c == '5')
        return GameGridCell.TX5;
    if (c == 'w')
        return GameGridCell.PORTAL_WHITE;
    if (c == 'y')
        return GameGridCell.PORTAL_YELLOW;
    if (c == 'o')
        return GameGridCell.PORTAL_DARK_GOLD;
    if (c == 'a')
        return GameGridCell.PORTAL_DARK_GRAY;
    return GameGridCell.INVALID;
  }
}
