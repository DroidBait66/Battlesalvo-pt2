package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user player of BattleSalvo
 */
public class ManualPlay extends AbstractPlayer {
  // dependency injection - pass in a class that can be updated by controller
  private final GameEventReport gameEventReport;
  private final String name;

  /**
   * Creates a Console Player Object with the given name and references to report objects
   *
   * @param name string representation of the name of this player
   * @param shotReport the dependency injected object to help transfer data to controller
   * @param gameEventReport dependency injected object to help transfer data to controller
   */
  public ManualPlay(String name, ShotReport shotReport, GameEventReport gameEventReport) {
    super(shotReport);
    this.name = name;
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
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    gameEventReport.giveSuccessfulHits(shotsThatHitOpponentShips);
  }

  @Override
  public void endGame(GameResult result, String reason) {
    gameEventReport.updateGameResult(result);
    gameEventReport.updateMessage(reason);
  }
}
