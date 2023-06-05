package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents the computer player for the BattleSalvo game
 */
@SuppressWarnings("DuplicatedCode")
public class AiPlayer implements Player {
  private int height;
  private int width;
  private GameBoard gameBoard;
  private ArrayList<Ship> fleet = new ArrayList<>();
  private final ShotReport shotReport;
  private final ArrayList<Coord> prevShots = new ArrayList<>();
  private final ArrayList<Coord> shotsThatHitOpponent = new ArrayList<>();

  /**
   * Creates an AiPlayer object with a reference to the given shot report
   *
   * @param aiShotReport the shot report object used as dependency injection to transfer data
   */
  public AiPlayer(ShotReport aiShotReport) {
    this.shotReport = aiShotReport;
  }

  /**
   * Returns this player's name - AI name is always "computer"
   *
   * @return string representation of this player's name - always computer for the AI
   */
  @Override
  public String name() {
    return "computer";
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
   * Returns this player's list of ships - mostly for testing
   *
   * @return list of ships representing this player's fleet
   */
  public ArrayList<Ship> getFleet() {
    return new ArrayList<>(this.fleet);
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
      //System.out.println(gameBoard.getBoard());
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
      if (random.nextInt(2) == 0) {
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
   * Returns the list of this player's shots this round
   *
   * @return the list of shots by this player
   */
  @Override
  public List<Coord> takeShots() {
    Random random = new Random();
    ArrayList<Coord> shotsList = new ArrayList<>();
    int numShots = this.shotReport.getNumShips();

    while (shotsList.size() < numShots) {
      int randX = random.nextInt(width);
      int randY = random.nextInt(height);
      Coord tempCoord = new Coord(randX, randY);
      if (!prevShots.contains(tempCoord)) {
        shotsList.add(tempCoord);
        prevShots.add(tempCoord);
      }
    }
    return shotsList;
  }

  /**
   * Returns a filtered list of the given list of coords for those shots that have damaged ships
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return list representing shots from the opponent that hit this player's ships
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    this.gameBoard.updateFromSalvo(opponentShotsOnBoard);
    this.shotReport.setGameBoard(this.gameBoard);
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
    this.shotReport.updateNumShips(this.fleet);
    return hits;
  }

  /**
   * Updates the data member to save a list of shots the have hit the opponent's ships
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    this.shotsThatHitOpponent.addAll(shotsThatHitOpponentShips);
  }

  /**
   * Retrieves this AiPlayer's list of shots that previously hit the opponent
   *
   * @return list of coords representing hits by the computer against the opponent
   */
  public ArrayList<Coord> getShotsThatHitOpponent() {
    return new ArrayList<>(this.shotsThatHitOpponent);
  }

  /**
   * Alerts the "player" of the end of game state and how the game ended
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    // AI player does not need to know how the game ended
  }
}
