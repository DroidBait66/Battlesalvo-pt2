package cs3500.pa03.model;

/**
 * Represents the 4 possible types of BattleSalvo ships
 */
public enum ShipType {
  /**
   * Represents a 6 unit long Carrier ship
   */
  CARRIER(6),
  /**
   * Represents a 5 unit long Battleship
   */
  BATTLESHIP(5),
  /**
   * Represents a 4 unit long Destroyer
   */
  DESTROYER(4),
  /**
   * Represents a 3 unit long Submarine
   */
  SUBMARINE(3);

  private final int shipSize;

  ShipType(int shipSize) {
    this.shipSize = shipSize;
  }

  /**
   * Provides the length for this ship type
   *
   * @return int corresponding to the unit length of the ship type
   */
  public int getShipSize() {
    return this.shipSize;
  }
}
