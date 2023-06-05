package cs3500.pa03.view;

import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;

/**
 * Interface of the View for the BattleSalvo game
 */
public interface ViewSalvo {
  /**
   * Displays the welcome message to the user and asks for board dimensions
   */
  void displayWelcome();

  /**
   * Tells the user their previous inputs do not match the necessary requirements and ask them to
   * input again
   */
  void displayInvalidDimensions();

  /**
   * Instructs the user to enter the number of each type of ship, with the existing restrictions
   * Also displays user's fleet positioning before the game begins
   *
   * @param maxShips the maximum number of ships that the user can have in their fleet
   */
  void displayFleetSelection(int maxShips);

  /**
   * Tells the user that they input an invalid number of ships to their fleet and asks them to try
   * again
   *
   * @param maxShips the max number of ships the user can have in their fleet
   */
  void displayInvalidFleet(int maxShips);

  /**
   * Displays the current state of the game for the player
   *
   * @param p1 the GameBoard for the user with their ships and their enemies previous shots
   * @param p2 the GameBoard of the opponent with only the users hits and misses
   */
  void displayGame(GameBoard p1, GameBoard p2);

  /**
   * Asks the user to input the given number of valid shots towards the enemy board
   *
   * @param numShots the integer representing the player's fleet's number of shots
   */
  void askForMissileLocations(int numShots);

  /**
   * Asks the user for their volley again because some number in the previous was invalid
   *
   * @param numsShots the number of shots in this volley
   */
  void invalidMissileLocations(int numsShots);

  /**
   * Tells the user the result of the game and why
   *
   * @param result One of win, lose, tie to tell the user how the game ended
   * @param reason why the game ended in the given way
   */
  void displayEndOfGame(GameResult result, String reason);

  /**
   * Displays the given message for exceptional situations
   *
   * @param message the message to be displayed
   */
  void displayMessage(String message);
}
