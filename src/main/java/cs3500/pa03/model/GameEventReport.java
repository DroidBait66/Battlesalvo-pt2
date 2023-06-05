package cs3500.pa03.model;

import java.util.List;

/**
 * Object to help transfer data between manual player and the controller about game events
 */
public class GameEventReport {
  private String msg;
  private GameResult gameResult;

  /**
   * Returns this object's message
   *
   * @return String representing the message of this report
   */
  public String getMessage() {
    return this.msg;
  }

  /**
   * Returns this Report's gameResult enum
   *
   * @return GameResult value of this report
   */
  public GameResult getGameResult() {
    return this.gameResult;
  }

  /**
   * Updates this report's data member to the given string
   *
   * @param msg String representing the new message to be stored
   */
  public void updateMessage(String msg) {
    this.msg = msg;
  }

  /**
   * Sets this report's data member to the given GameResult value to be stored
   *
   * @param gameResult GameResult enum value to be stored in this object
   */
  public void updateGameResult(GameResult gameResult) {
    this.gameResult = gameResult;
  }

  /**
   * Creates a string message that represents the coordinates of shots that hit the opponent
   *
   * @param shotsThatHitOpponentsShips list of shots that hit the opponenent last volley
   */
  public void giveSuccessfulHits(List<Coord> shotsThatHitOpponentsShips) {
    StringBuilder stringbuilder = new StringBuilder();
    for (Coord coord : shotsThatHitOpponentsShips) {
      String shotMsg = coord + " was a Hit!\n";
      stringbuilder.append(shotMsg);
    }
    this.msg = stringbuilder.toString();
  }

}
