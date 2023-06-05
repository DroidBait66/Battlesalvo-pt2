package cs3500.pa03.view;

import cs3500.pa03.model.BoardCell;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Implementation of the view for the BattleSalvo game based on the command line
 */
public class DisplaySalvo implements ViewSalvo {
  private final PrintStream printStream;

  /**
   * Creates a new view object with the given output stream
   *
   * @param outputStream that corresponds to the way in which information will be displayed
   */
  public DisplaySalvo(OutputStream outputStream) {
    this.printStream = new PrintStream(outputStream);

  }

  /**
   * Asks the user for their desired board dimensions
   */
  @Override
  public void displayWelcome() {
    String welcomeMsg = """
       Welcome to BattleSalvo!
       Please enter a height and width between 6 and 15 inclusive
       -->""";
    printStream.print(welcomeMsg);
  }

  /**
   * Asks the user to re-input dimensions that are valid
   */
  @Override
  public void displayInvalidDimensions() {
    String invalidDimensions = """
        The dimensions you entered were invalid.
        Please only enter dimensions between 6 and 15 inclusive
        -->""";
    printStream.print(invalidDimensions);
  }

  /**
   * Asks the user for fleet numbers for each ship type
   *
   * @param maxShips the maximum number of ships that the user can have in their fleet
   */
  @Override
  public void displayFleetSelection(int maxShips) {
    String selectFleet = """
        Please enter an integer for each type of ship.
        Your fleet must contain at least one of each
        type of the following ships: Carrier, Battleship, Destroyer, Submarine
        The maximum number of ships in your fleet is\s""" + maxShips + """
        
        -->""";
    printStream.print(selectFleet);
  }

  /**
   * Asks the user to re-input valid fleet numbers
   *
   * @param maxShips the max number of ships the user can have in their fleet
   */
  @Override
  public void displayInvalidFleet(int maxShips) {
    String selectFleetAgain = """
        There must be at least one of every ship and you may not have more than\s""" + maxShips
        + """ 
         Please enter an integer for each of the following ship types:
         Carrier, Battleship, Destroyer, Submarine
         -->""";
    printStream.print(selectFleetAgain);
  }

  /**
   * Shows the state of the game to the console player
   *
   * @param consolePlayer the GameBoard for the user with their ships and their enemies previous
   shots
   * @param computer the GameBoard of the opponent with only the users hits and misses
   */
  @Override
  public void displayGame(GameBoard consolePlayer, GameBoard computer) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Opponent's Board:\n");
    this.constructBoard(computer.getBoardForOpponent(), stringBuilder);
    stringBuilder.append("\nYour Board:\n");
    this.constructBoard(consolePlayer.getBoard(), stringBuilder);

    printStream.print(stringBuilder);
  }

  /**
   * Creates the board based on the 2D arraylist using the given string builder
   *
   * @param currentBoard the 2D arraylist representing the board to be output in the string builder
   * @param stringBuilder the current string builder to which this board will be appended
   */
  private void constructBoard(ArrayList<ArrayList<BoardCell>> currentBoard,
                              StringBuilder stringBuilder) {
    for (ArrayList<BoardCell> boardCells : currentBoard) {
      for (BoardCell boardCell : boardCells) {
        stringBuilder.append(boardCell.getMarker());
      }
      stringBuilder.append("\n");
    }
  }

  /**
   * Asks the user for the given number of shots against the enemy board
   *
   * @param numShots the integer representing the player's fleet's number of shots
   */
  @Override
  public void askForMissileLocations(int numShots) {
    String askForShots = "\nPlease enter " + numShots + " Shots:\n-->";
    printStream.print(askForShots);
  }

  /**
   * Asks for the volley again after an invalid number was entered
   *
   * @param numShots the number of shots in this volley
   */
  @Override
  public void invalidMissileLocations(int numShots) {
    String askForShotsAgain = "\nSome value you entered is invalid\nPlease enter " + numShots
        + " shots against your opponent:\n-->";
    printStream.print(askForShotsAgain);
  }

  /**
   * Shows the user the results of the game
   *
   * @param result One of win, lose, tie to tell the user how the game ended
   * @param reason why the game ended in the given way
   */
  @Override
  public void displayEndOfGame(GameResult result, String reason) {
    String endOfGame = "The game has ended in a " + result.name() + "\nBecause " + reason;
    printStream.println(endOfGame);
  }

  @Override
  public void displayMessage(String message) {
    printStream.println(message);
  }
}
