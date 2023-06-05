package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a user player of BattleSalvo
 */
@SuppressWarnings("ALL")
public class ManualPlay implements Player {
  // dependency injection - pass in a class that can be updated by controller
  private final ShotReport shotReport;
  private final GameEventReport gameEventReport;
  private final String name;
  private GameBoard gameBoard;
  private int height;
  private int width;
  private ArrayList<Ship> fleet = new ArrayList<>();

  /**
   * Creates a Console Player Object with the given name and references to report objects
   *
   * @param name string representation of the name of this player
   * @param shotReport the dependency injected object to help transfer data to controller
   * @param gameEventReport dependency injected object to help transfer data to controller
   */
  public ManualPlay(String name, ShotReport shotReport, GameEventReport gameEventReport) {
    this.name = name;
    this.shotReport = shotReport;
    this.gameEventReport = gameEventReport;
  }

  /**
   * returns the name of this player
   *
   * @return the string representing the name of this player
   */
  @Override
  public String name() {
    return name;
  }

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

  /**
   * Returns the list of this player's ships
   *
   * @return the list of this player's fleet
   */
  public ArrayList<Ship> getFleet() {
    return new ArrayList<>(this.fleet);
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public ArrayList<Coord> takeShots() {
    return shotReport.getShots();
  }

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
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    gameEventReport.giveSuccessfulHits(shotsThatHitOpponentShips);
  }

  @Override
  public void endGame(GameResult result, String reason) {
    gameEventReport.updateGameResult(result);
    gameEventReport.updateMessage(reason);
  }
}
