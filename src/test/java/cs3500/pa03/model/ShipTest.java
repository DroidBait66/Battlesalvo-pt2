package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  private Coord coord1 = new Coord(0, 1);
  private Coord coord2 = new Coord(0, 2);
  private Coord coord3 = new Coord(0, 3);
  private ArrayList<Coord> coords = new ArrayList<>(Arrays.asList(coord1, coord2, coord3));
  private Ship ship = new Ship(ShipType.SUBMARINE, coords);

  @BeforeEach
  void setUp() {
    coord1 = new Coord(0, 1);
    coord2 = new Coord(0, 2);
    coord3 = new Coord(0, 3);
    coords = new ArrayList<>(Arrays.asList(coord1, coord2, coord3));
    ship = new Ship(ShipType.SUBMARINE, coords);
  }

  @Test
  void checkConstructorException() {
    Coord extraCoord = new Coord(0, 4);
    coords.add(extraCoord);
    assertThrows(IllegalArgumentException.class, () -> new Ship(ShipType.SUBMARINE, coords));
  }

  @Test
  void getType() {
    ShipType shipType = ShipType.SUBMARINE;
    assertEquals(shipType, ship.getType());
  }

  @Test
  void getCoords() {
    ArrayList<Coord> testCoords = new ArrayList<>(coords);
    assertEquals(testCoords, ship.getCoords());
  }

  @Test
  void getDamage() {
    ArrayList<Coord> emptyCoords = new ArrayList<>();
    assertEquals(emptyCoords, ship.getDamage());
  }

  @Test
  void damageShip() {
    ArrayList<Coord> shotCoords = new ArrayList<>();
    assertEquals(shotCoords, ship.getDamage());

    Coord shot1 = new Coord(0, 2);
    Coord shotNotOnShip = new Coord(1, 1);
    Coord alreadyShot = new Coord(0, 2);
    shotCoords.add(shot1);
    shotCoords.add(shotNotOnShip);
    shotCoords.add(alreadyShot);

    for (Coord shot : shotCoords) {
      ship.damageShip(shot);
    }

    shotCoords.remove(shotNotOnShip);
    shotCoords.remove(alreadyShot);
    assertEquals(shotCoords, ship.getDamage());
  }

  @Test
  void isSunk() {
    assertFalse(ship.isSunk());
    ArrayList<Coord> shotCoords = new ArrayList<>(coords);

    for (Coord shot : shotCoords) {
      ship.damageShip(shot);
    }
    assertTrue(ship.isSunk());
  }

  @Test
  void checkToString() {
    String expected = "SUBMARINE with coords: [(0, 1), (0, 2), (0, 3)]";
    assertEquals(expected, ship.toString());
  }
}