package cs3500.pa03.model;

/**
 * Represents the possible elements on a single cell of the BattleSalvo game board
 */
public enum BoardCell {
  /**
   * Represents an empty cell on the game board
   */
  EMPTY("- "),
  /**
   * Represents a ship location on the game board
   */
  SHIP("S "),
  /**
   * Represents an opponent's shot that missed a ship
   */
  MISS("M "),
  /**
   * Represents an opponent's shot that hit a ship
   */
  HIT("H ");

  private final String marker;

  BoardCell(String asString) {
    this.marker = asString;
  }

  /**
   * Produces the string value of each possible element on the cell
   *
   * @return a string representing the possible state of the a cell on the board
   */
  public String getMarker() {
    return this.marker;
  }

}
