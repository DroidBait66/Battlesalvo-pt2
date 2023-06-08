package cs3500.pa04;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;

/**
 * Converts ships into ones that store starting location, length, and direction
 */
public class ShipAdapter {
  private final Coord start;
  private final int length;
  private final Direction dir;

  /**
   * Converts the given ship into a ship adapter, that stores same ship with different info
   */
  public ShipAdapter(Ship ship) {
    this.start = ship.getCoords().get(0);
    this.length = ship.getCoords().size();
    this.dir = findOrientation(ship.getCoords());
  }

  /**
   * Creates a new shipAdapter with the given args, allows Jackson to create JSON ship objects
   *
   * @param start the starting coordinate of the ship
   * @param length the number of coords that the ship takes up
   * @param dir the direction/orientation of the ship (either horizontal or vertical)
   */
  @JsonCreator
  public ShipAdapter(@JsonProperty("coord") Coord start,
                     @JsonProperty("length") int length,
                     @JsonProperty("direction") Direction dir) {
    this.start = start;
    this.length = length;
    this.dir = dir;
  }

  /**
   * Finds the Direction that ship with the given coords is facing
   *
   * @param coords the list of coords for the ship
   * @return the Direction in which the ship is facing
   */
  private Direction findOrientation(ArrayList<Coord> coords) {
    if (coords.get(0).getX() != coords.get(1).getX()) {
      // Ship's coords change along the X axis, meaning the ship is horizontal
      return Direction.HORIZONTAL;
    } else {
      return Direction.VERTICAL;
    }
  }

  /**
   * Gets the ship adapter's starting coordinate
   *
   * @return Coord representing the starting location of the ship
   */
  public Coord getStart() {
    return this.start;
  }

  /**
   * Gets the length of the ship
   *
   * @return int representing the number of cell's or coordinates the ship is
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Gets the direction of the ship
   *
   * @return the direction that the ship is facing
   */
  public Direction getDir() {
    return this.dir;
  }

}

