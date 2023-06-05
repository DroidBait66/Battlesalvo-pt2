package cs3500.pa03.model;

/**
 * Represents a coordinate on the game board
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;

  /**
   * Creates a new coordinate object with the given x and y integer coordinates
   *
   * @param xcoord int corresponding to the x coord of this object
   * @param ycoord int corresponding to the y coord of this object
   */
  public Coord(int xcoord, int ycoord) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
  }

  /**
   * Returns this Coord's X value
   *
   * @return int corresponding to the X-Coordinate of this Coord
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * Returns this Coord's Y value
   *
   * @return int corresponding to the Y-Coordinate of this Coord
   */
  public int getY() {
    return this.ycoord;
  }

  /**
   * Provides this coord in a readable string format
   *
   * @return a string representing this coord
   */
  public String toString() {
    return "(" + this.xcoord + ", " + this.ycoord + ")";
  }

  /**
   * Compares this coord and another object for equality
   *
   * @param other another object to test equality with this coord
   * @return boolean representing whether this and other are equal object
   */
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Coord coord)) {
      return false;
    }
    return this.xcoord == coord.getX() && this.ycoord == coord.getY();
  }
}
