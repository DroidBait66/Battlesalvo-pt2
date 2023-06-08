package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the computer player for the BattleSalvo game
 */
public class AiPlayer extends AbstractPlayer {
  private final ArrayList<Coord> prevShots = new ArrayList<>();
  private final ArrayList<Coord> shotsThatHitOpponent = new ArrayList<>();

  /**
   * Creates an AiPlayer object with a reference to the given shot report
   *
   * @param aiShotReport the shot report object used as dependency injection to transfer data
   */
  public AiPlayer(ShotReport aiShotReport) {
    super(aiShotReport);
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
   * Updates the data member to save a list of shots that have hit the opponent's ships
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
