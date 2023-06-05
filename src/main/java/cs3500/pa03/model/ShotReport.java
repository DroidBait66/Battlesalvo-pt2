package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Class to hold and update shot locations for the player class
 */
public class ShotReport {
  private ArrayList<Coord> shots = new ArrayList<>();
  private int numShips;
  private GameBoard gameBoard;

  /**
   * Gives the list of coordinates that represents the users shots for this round
   *
   * @return the list of coordinates that represent the player's shots for this round
   */
  public ArrayList<Coord> getShots() {
    return new ArrayList<>(this.shots);
  }

  /**
   * Updates the lists of shots to the given coords
   *
   * @param newShots the list of coords to update the current list of coords
   */
  public void launchMissiles(ArrayList<Coord> newShots) {
    if (newShots.size() > numShips) {
      throw new IllegalArgumentException("Number of shots is greater than remaining ships");
    } else {
      this.shots = newShots;
    }
  }

  /**
   * Deletes the current list of coords
   */
  public void clear() {
    this.shots = new ArrayList<>();
  }

  /**
   * Returns the number of ships for the fleet stored by this report object
   *
   * @return int representing the number of ships remaining for this fleet
   */
  public int getNumShips() {
    return this.numShips;
  }

  /**
   * Updates the number of ships in the fleet by detecting those that have sunk in the given list
   *
   * @param fleet original list of ships in the fleet, only counts those that are not sunk
   */
  public void updateNumShips(ArrayList<Ship> fleet) {
    numShips = 0;
    for (Ship ship : fleet) {
      if (!ship.isSunk()) {
        numShips++;
      }
    }
  }

  /**
   * Updates this report with a GameBoard object to be stored
   *
   * @param gameBoard GameBoard object that corresponds to the player's board
   */
  public void setGameBoard(GameBoard gameBoard) {
    this.gameBoard = gameBoard;
  }

  /**
   * Returns this report's game board at the current state
   *
   * @return GameBoard object representing this report's board of BattleSalvo
   */
  public GameBoard getGameBoard() {
    return this.gameBoard;
  }


}
