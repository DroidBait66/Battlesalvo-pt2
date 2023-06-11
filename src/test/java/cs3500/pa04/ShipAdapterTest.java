package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for ship adaptor
 */
public class ShipAdapterTest {

  private Coord c1 = new Coord(0, 0);
  private Coord c2 = new Coord(0, 1);
  private Coord c3 = new Coord(0, 2);
  private Coord c4 = new Coord(1, 0);
  private Coord c5 = new Coord(2, 0);
  private ArrayList<Coord> shipCoords = new ArrayList<>(Arrays.asList(c1, c2, c3));
  private Ship ship1 = new Ship(ShipType.SUBMARINE, shipCoords);

  /**
   * set up method for ships
   */
  @BeforeEach
  void setUp() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 1);
    c3 = new Coord(0, 2);
    c4 = new Coord(1, 0);
    c5 = new Coord(2, 0);
    shipCoords = new ArrayList<>(Arrays.asList(c1, c2, c3));
    ship1 = new Ship(ShipType.SUBMARINE, shipCoords);
  }

  /**
   * tests the getCoord method
   */
  @Test
  void getCoord() {
    ShipAdapter shipAdapter = new ShipAdapter(ship1);
    Coord expected = c1;
    assertEquals(expected, shipAdapter.getCoord());
  }

  /**
   * tests the get length method
   */
  @Test
  void getLength() {
    ShipAdapter shipAdapter =
        new ShipAdapter(c1, ShipType.SUBMARINE.getShipSize(), Direction.VERTICAL);
    int expected = ShipType.SUBMARINE.getShipSize();
    assertEquals(expected, shipAdapter.getLength());
  }

  /**
   * tests the get direction method
   */
  @Test
  void getDirection() {
    ArrayList<Coord> shipCoordsHorizontal = new ArrayList<>(Arrays.asList(c1, c4, c5));
    Ship ship = new Ship(ShipType.SUBMARINE, shipCoordsHorizontal);
    ShipAdapter shipAdapter = new ShipAdapter(ship);
    Direction expected = Direction.HORIZONTAL;
    assertEquals(expected, shipAdapter.getDirection());
  }
}
