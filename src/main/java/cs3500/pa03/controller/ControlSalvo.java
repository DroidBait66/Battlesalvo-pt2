package cs3500.pa03.controller;

/**
 * Controller interface for the BattleSalvo game
 */
public interface ControlSalvo {

  /**
   * Welcomes the user into the game and asks/accepts board dimensions
   */
  void intro();

  /**
   * Tells the player which types of ships are available and the limitations on number, and accepts
   * numbers of ships
   */
  void fleetSelection();

  /**
   * Controls the playing of the game itself, asking for shot coords and reporting damages from
   * the computer player
   */
  void runSalvo();

  /**
   * Detects if the game
   *
   * @return boolean: true if game is over; false otherwise
   */
  boolean isGameOver();
}
