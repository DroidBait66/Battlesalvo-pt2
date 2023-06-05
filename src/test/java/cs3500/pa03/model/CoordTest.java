package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {

  private int xcoord = 2;
  private int ycoord = 4;
  private Coord coord = new Coord(xcoord, ycoord);

  @BeforeEach
  void setUp() {
    xcoord = 2;
    ycoord = 4;
    coord = new Coord(xcoord, ycoord);
  }

  @Test
  void getX() {
    assertEquals(2, coord.getX());
  }

  @Test
  void getY() {
    assertEquals(4, coord.getY());
  }

  @Test
  void testToString() {
    String result = "(2, 4)";
    assertEquals(result, coord.toString());
  }

  @Test
  void testEquals() {
    Ship notCoord = new Ship(ShipType.SUBMARINE, new ArrayList<>(Arrays.asList(null, null, null)));
    assertNotEquals(coord, notCoord);

    Coord equalCoord = new Coord(2, 4);
    assertEquals(coord, equalCoord);
  }
}