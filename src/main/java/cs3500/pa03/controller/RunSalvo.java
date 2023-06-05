package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameEventReport;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlay;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.ShotReport;
import cs3500.pa03.view.ViewSalvo;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Controller implementation for the BattleSalvo game
 */
public class RunSalvo implements ControlSalvo {

  private final ShotReport consoleShotReport;
  private final GameEventReport consoleEventReport = new GameEventReport();
  private final Player consolePlayer;

  private final ShotReport aiShotReport;
  private final Player aiPlayer;
  private final ViewSalvo view;

  private final Scanner scanner;

  // board dimensions
  private int height;
  private int width;

  private int maxShips;

  /**
   * Constructs a controller object using the given players and view module
   *
   * @param view        the class to display all outputs for the user
   * @param inputStream the source from which the scanner will find user inputs
   */
  public RunSalvo(ViewSalvo view, InputStream inputStream) {
    this.view = view;
    scanner = new Scanner(inputStream);
    consoleShotReport = new ShotReport();
    consolePlayer = new ManualPlay("console", consoleShotReport, consoleEventReport);
    aiShotReport = new ShotReport();
    aiPlayer = new AiPlayer(aiShotReport);
  }

  /**
   * Welcomes the user into the game and asks/accepts board dimensions
   */
  @Override
  public void intro() {
    view.displayWelcome();
    int height = -1;
    int width = -1;
    boolean hasVals = false;
    while (!hasVals) {
      int tempHeight = scanner.nextInt();
      int tempWidth = scanner.nextInt();

      if ((tempHeight >= 6 && tempHeight <= 15) && (tempWidth >= 6 && tempWidth <= 15)) {
        height = tempHeight;
        width = tempWidth;
        hasVals = true;
      } else {
        view.displayInvalidDimensions();
      }
    }

    this.height = height;
    this.width = width;
  }

  /**
   * Tells the player which types of ships are available and the limitations on number, and accepts
   * numbers of ships
   */
  @Override
  public void fleetSelection() {
    maxShips = Math.min(height, width);
    view.displayFleetSelection(maxShips);
    int[] shipNums;
    shipNums = this.takeFleetInput();
    while (!validArgs(shipNums)) {
      view.displayInvalidFleet(maxShips);
      shipNums = this.takeFleetInput();
    }
    this.fleetSetting(shipNums);
  }

  /**
   * Accepts input from the scanner for 4 integers which correspond to the 4 ship types
   *
   * @return an array of integers representing the number of each type of ship in ship size order
   */
  private int[] takeFleetInput() {
    int[] shipNumbers = new int[4];
    int count = 0;
    while (count < 4) {
      shipNumbers[count] = scanner.nextInt();
      count++;
    }
    return shipNumbers;
  }

  /**
   * Makes sure user input matches restriction of the board and passes that info to the player
   * objects
   *
   * @param shipNumbers array of integers corresponding to the user's number of each ship type
   */
  private void fleetSetting(int[] shipNumbers) {
    int numCarriers = shipNumbers[0];
    int numBattleships = shipNumbers[1];
    int numDestroyers = shipNumbers[2];
    int numSubmarines = shipNumbers[3];

    HashMap<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, numCarriers);
    specs.put(ShipType.BATTLESHIP, numBattleships);
    specs.put(ShipType.DESTROYER, numDestroyers);
    specs.put(ShipType.SUBMARINE, numSubmarines);
    consolePlayer.setup(this.height, this.width, specs);
    aiPlayer.setup(this.height, this.width, specs);
  }

  private boolean validArgs(int[] shipNumbers) {
    int numCarriers = shipNumbers[0];
    int numBattleships = shipNumbers[1];
    int numDestroyers = shipNumbers[2];
    int numSubmarines = shipNumbers[3];

    boolean allShipTypes =
        numCarriers != 0 && numBattleships != 0 && numDestroyers != 0 && numSubmarines != 0;
    boolean notTooMany =
        (numCarriers + numBattleships + numDestroyers + numSubmarines) <= maxShips;
    return allShipTypes && notTooMany;
  }


  @Override
  public void runSalvo() {
    while (!isGameOver()) {
      // display the current state of the board
      view.displayGame(consoleShotReport.getGameBoard(), aiShotReport.getGameBoard());

      // find the number of shots for each "player"
      int consoleNumShots = this.limitShots(consoleShotReport.getNumShips(), aiShotReport);
      // asks for coordinates from the user
      view.askForMissileLocations(consoleNumShots);
      ArrayList<Coord> shotLocations = shotProcedure(consoleNumShots);
      consoleShotReport.clear();
      consoleShotReport.launchMissiles(shotLocations);

      // finding hits/misses and updating game boards
      List<Coord> playerHitsOnAi = aiPlayer.reportDamage(consolePlayer.takeShots());
      List<Coord> aiHitsOnPlayer = consolePlayer.reportDamage(aiPlayer.takeShots());
      // alerting the "players" to which hits were successful
      aiPlayer.successfulHits(aiHitsOnPlayer);
      consolePlayer.successfulHits(playerHitsOnAi);
      view.displayMessage(consoleEventReport.getMessage());
    }

    this.detectWinner();
  }

  private ArrayList<Coord> shotProcedure(int numShips) {
    ArrayList<Coord> shots = takeShotInput(numShips);

    while (!validShotCoords(shots)) {
      view.invalidMissileLocations(numShips);
      shots = takeShotInput(numShips);
    }
    return shots;
  }

  private ArrayList<Coord> takeShotInput(int numShips) {
    ArrayList<Coord> shots = new ArrayList<>();
    int count = 0;
    while (count < numShips) {
      if (!scanner.hasNext()) {
        break;
      }
      int xcoord = scanner.nextInt();
      int ycoord = scanner.nextInt();
      Coord tempCoord = new Coord(xcoord, ycoord);
      shots.add(tempCoord);
      scanner.nextLine();
      count++;
    }

    return shots;
  }

  /**
   * Ensures all coords entered are valid on the GameBoard
   *
   * @param shotCoords possible coords to be validated
   * @return boolean: true if valid shots; false otherwise
   */
  private boolean validShotCoords(ArrayList<Coord> shotCoords) {
    int maxDimension = Math.max(height, width);
    boolean result = true;
    for (Coord coord : shotCoords) {
      if (coord.getX() < 0 || coord.getX() >= maxDimension) {
        result = false;
      } else if (coord.getY() < 0 || coord.getY() >= maxDimension) {
        result = false;
      }
    }
    return result;
  }

  private int limitShots(int numShips, ShotReport shotReport) {
    int emptySpaces = shotReport.getGameBoard().getEmptySpaces();
    return Math.min(numShips, emptySpaces);

  }

  private void detectWinner() {
    if (consoleShotReport.getNumShips() == 0 && aiShotReport.getNumShips() == 0) {
      consolePlayer.endGame(GameResult.TIE, "Both player's ships are sunk!");
      view.displayEndOfGame(GameResult.TIE, "Both player's ships are sunk!");
      aiPlayer.endGame(GameResult.TIE, "Both player's ships are sunk!");
    } else if (consoleShotReport.getNumShips() == 0) {
      consolePlayer.endGame(GameResult.LOSE, "Your opponent sunk your ships!");
      view.displayEndOfGame(GameResult.LOSE, "Your opponent sunk your ships!");
      aiPlayer.endGame(GameResult.WIN, "You sunk all your opponent's ships!");
    } else {
      consolePlayer.endGame(GameResult.WIN, "You sunk all your opponent's ships!");
      view.displayEndOfGame(GameResult.WIN, "You sunk all your opponent's ships!");
      aiPlayer.endGame(GameResult.LOSE, "Your opponent sunk your ships!");
    }
  }

  @Override
  public boolean isGameOver() {
    return (consoleShotReport.getNumShips() == 0) || (aiShotReport.getNumShips() == 0);
  }

  // Use mock Models with  to test single method user inputs
}
