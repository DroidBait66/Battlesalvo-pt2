package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Abstraction of the Player interface implementations to reduce code duplication
 */
public abstract class AbstractPlayer implements Player {
  /**
   * The height of the game board for this player's game
   */
  protected int height;
  /**
   * The width of the game board for this player's game
   */
  protected int width;
  /**
   * The GameBoard object on which the ship and shot data is stored
   */
  protected GameBoard gameBoard;
  /**
   * The object which stores shot and ship information
   */
  protected final ShotReport shotReport;
  /**
   * The list of ships of this player
   */
  protected ArrayList<Ship> fleet;

  /**
   * Creates a new Player with the given ShotReport object
   *
   * @param shotReport the report object to access shot information
   */
  public AbstractPlayer(ShotReport shotReport) {
    this.shotReport = shotReport;
  }


  @Override
  public abstract String name();

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   *  on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public ArrayList<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.height = height;
    this.width = width;
    this.gameBoard = new GameBoard(this.height, this.width);
    int maxNumShips = Math.min(this.height, this.width);

    int numCarrier = specifications.get(ShipType.CARRIER);
    int numBattleship = specifications.get(ShipType.BATTLESHIP);
    int numDestroyer = specifications.get(ShipType.DESTROYER);
    int numSubmarine = specifications.get(ShipType.SUBMARINE);
    int numShips = numBattleship + numSubmarine + numCarrier + numDestroyer;

    if (numShips > maxNumShips) {
      throw new IllegalArgumentException("Max number of ships may not exceed " + maxNumShips);
    }

    // add all the ships created to the list of ships
    ArrayList<Ship> ships = new ArrayList<>();
    assignShips(ShipType.CARRIER, numCarrier, ships);
    assignShips(ShipType.BATTLESHIP, numBattleship, ships);
    assignShips(ShipType.DESTROYER, numDestroyer, ships);
    assignShips(ShipType.SUBMARINE, numSubmarine, ships);

    this.fleet = ships;
    this.shotReport.setGameBoard(this.gameBoard);
    this.shotReport.setFleet(this.fleet);
    this.shotReport.updateNumShips(this.fleet);
    return ships;
  }

  /**
   * Creates the given number of ships of the given type and adds them to the given list, also
   * adds to the ships to the gameBoard
   *
   * @param shipType the type of the ship being created
   * @param numShips the number of this type of ship to be created
   * @param ships the list of ships to which the created ships should be added
   */
  private void assignShips(ShipType shipType, int numShips, ArrayList<Ship> ships) {
    for (int i = 0; i < numShips; i++) {
      ArrayList<Coord> shipCoords = this.placeShip(shipType);
      Ship ship = new Ship(shipType, shipCoords);
      gameBoard.setUp(ship);
      ships.add(ship);
    }
  }

  /**
   * Detects if a spot is already taken and gives ships coordinates on the board
   *
   * @param shipType the type of ship that needs a location
   * @return the list of coordinates for 1 ship of the given shipType
   */
  private ArrayList<Coord> placeShip(ShipType shipType) {
    int shipSize = shipType.getShipSize();
    Random random = new Random();
    ArrayList<Coord> shipLocation = new ArrayList<>();
    // continue looking until all segments have coordinates
    while (shipLocation.size() < shipSize) {
      if (random.nextInt(3) == 0) {
        // horizontal
        int tempSpotX = random.nextInt(width - shipSize + 1);
        int tempSpotY = random.nextInt(height);
        for (int i = 0; i < shipSize; i++) {
          if (gameBoard.getBoard().get(tempSpotY).get(tempSpotX + i) == BoardCell.SHIP) {
            shipLocation = new ArrayList<>();
            break;
          } else {
            Coord shipSegment = new Coord(tempSpotX + i, tempSpotY);
            shipLocation.add(shipSegment);
          }
        }
      } else {
        // vertical
        int tempSpotX = random.nextInt(width);
        int tempSpotY = random.nextInt(height - shipSize + 1);
        for (int i = 0; i < shipSize; i++) {
          if (gameBoard.getBoard().get(tempSpotY + i).get(tempSpotX) == BoardCell.SHIP) {
            shipLocation = new ArrayList<>();
            break;
          } else {
            Coord shipSegment = new Coord(tempSpotX, tempSpotY + i);
            shipLocation.add(shipSegment);
          }
        }
      }
    }
    return shipLocation;
  }


  @Override
  public abstract List<Coord> takeShots();


  /**
   * Returns a filtered list of the given list of coords for those shots that have damaged ships
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return list representing shots from the opponent that hit this player's ships
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {

    //this.shotReport.setGameBoard(this.gameBoard);
    ArrayList<Coord> hits = new ArrayList<>();
    for (Ship ship : this.fleet) {
      ArrayList<Coord> curShipCoords = ship.getCoords();

      for (Coord shotCoord : opponentShotsOnBoard) {
        if (curShipCoords.contains(shotCoord)) {
          hits.add(shotCoord);
          ship.damageShip(shotCoord);
        }
      }
    }
    this.gameBoard.updateFromSalvo(opponentShotsOnBoard);
    this.shotReport.updateNumShips(this.fleet);
    return hits;
  }

  @Override
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);

  @Override
  public abstract void endGame(GameResult result, String reason);




}
