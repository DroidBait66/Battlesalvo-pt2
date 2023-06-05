package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Represents a single ship on the board
 */
public class Ship {
  private final ShipType shipType;
  private final ArrayList<Coord> coords;

  // represents the coords of the ship that have been hit by enemy shots
  private final ArrayList<Coord> damagedSegment = new ArrayList<>();

  private boolean sunk = false;

  /**
   * Produces a new ship object with the given type and coordinates
   *
   * @param shipType One of the possible ship types in the BattleSalvo game
   * @param coords a list of coords for each of the segments of the ship based on the ship type
   */
  public Ship(ShipType shipType, ArrayList<Coord> coords) {
    this.shipType = shipType;

    if (shipType.getShipSize() != coords.size()) {
      throw new IllegalArgumentException("A " + shipType.name() + " must have "
          + shipType.getShipSize());
    }
    this.coords = coords;
  }

  /**
   * Provides the ship type of this ship
   *
   * @return ShipType of this ship
   */
  public ShipType getType() {
    return this.shipType;
  }

  /**
   * Provides the list of coords of this ship
   *
   * @return the ArrayList of coords for this ships segments
   */
  public ArrayList<Coord> getCoords() {
    return new ArrayList<>(this.coords);
  }

  /**
   * Provides the coords of the damaged segments of this ship
   *
   * @return ArrayList of coords of damaged segments of this ship
   */
  public ArrayList<Coord> getDamage() {
    return new ArrayList<>(this.damagedSegment);
  }

  /**
   * Updates the coords of the damaged segments and determines if this ship is now sunk
   *
   * @param shotCoord the coordinates of the enemy shots
   */
  public void damageShip(Coord shotCoord) {
    if (this.coords.contains(shotCoord) && !(this.damagedSegment.contains(shotCoord))) {
      this.damagedSegment.add(shotCoord);
    }
    isSinking();
  }

  /**
   * Determines if this ship has all of its segments hit and is sunk
   */
  private void isSinking() {
    if (this.damagedSegment.size() >= this.coords.size()) {
      sunk = true;
    }
  }

  /**
   * Determines if this ship is sunk
   *
   * @return boolean representing whether or not this ship is sunk
   */
  public boolean isSunk() {
    return sunk;
  }

  public String toString() {
    return shipType.name() + " with coords: " + coords;
  }
}
