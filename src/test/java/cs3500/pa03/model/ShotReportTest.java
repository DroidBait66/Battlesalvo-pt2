package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShotReportTest {
  private Coord coord1 = new Coord(0, 1);
  private Coord coord2 = new Coord(0, 2);
  private Coord coord3 = new Coord(0, 3);
  private ArrayList<Coord> coords = new ArrayList<>(Arrays.asList(coord1, coord2, coord3));
  private Ship ship = new Ship(ShipType.SUBMARINE, coords);
  private ShotReport shotReport = new ShotReport();
  private Coord coord4 = new Coord(1, 1);
  private Coord coord5 = new Coord(1, 2);
  private Coord coord6 = new Coord(1, 3);
  private ArrayList<Coord> coords2 = new ArrayList<>(Arrays.asList(coord4, coord5, coord6));
  private Ship ship2 = new Ship(ShipType.SUBMARINE, coords2);

  @BeforeEach
  void setUp() {
    shotReport = new ShotReport();
    coord1 = new Coord(0, 1);
    coord2 = new Coord(0, 2);
    coord3 = new Coord(0, 3);
    coords = new ArrayList<>(Arrays.asList(coord1, coord2, coord3));
    ship = new Ship(ShipType.SUBMARINE, coords);

    coord4 = new Coord(1, 1);
    coord5 = new Coord(1, 2);
    coord6 = new Coord(1, 3);
    coords2 = new ArrayList<>(Arrays.asList(coord4, coord5, coord6));
    ship2 = new Ship(ShipType.SUBMARINE, coords2);

    for (Coord coord : coords2) {
      ship2.damageShip(coord);
    }

  }

  @Test
  void getShots() {
    assertEquals(new ArrayList<Coord>(), shotReport.getShots());

    ArrayList<Ship> fleet = new ArrayList<>(Arrays.asList(ship, ship2));
    ArrayList<Coord> smallCoords = new ArrayList<>(List.of(new Coord(0, 2)));
    shotReport.updateNumShips(fleet);

    shotReport.launchMissiles(smallCoords);
    assertEquals(smallCoords, shotReport.getShots());
  }

  @Test
  void launchMissiles() {
    assertEquals(new ArrayList<Coord>(), shotReport.getShots());
    ArrayList<Ship> fleet = new ArrayList<>(Arrays.asList(ship, ship2));
    ArrayList<Coord> smallCoords = new ArrayList<>(List.of(new Coord(0, 2)));
    shotReport.updateNumShips(fleet);

    shotReport.launchMissiles(smallCoords);
    assertEquals(smallCoords, shotReport.getShots());
  }

  @Test
  void launchMissilesException() {
    assertThrows(IllegalArgumentException.class, () -> shotReport.launchMissiles(coords));
  }

  @Test
  void clear() {
    ShotReport shotReport = new ShotReport();
    ArrayList<Coord> smallCoords = new ArrayList<>(List.of(new Coord(0, 2)));
    ArrayList<Ship> fleet = new ArrayList<>(Arrays.asList(ship, ship2));
    shotReport.updateNumShips(fleet);
    shotReport.launchMissiles(smallCoords);
    assertEquals(smallCoords, shotReport.getShots());

    shotReport.clear();
    assertEquals(new ArrayList<Coord>(), shotReport.getShots());
  }

  @Test
  void getNumShips() {
    assertEquals(0, shotReport.getNumShips());
    shotReport.updateNumShips(new ArrayList<>(List.of(ship)));
    assertEquals(1, shotReport.getNumShips());
  }

  @Test
  void updateNumShips() {
    assertEquals(0, shotReport.getNumShips());
    shotReport.updateNumShips(new ArrayList<>(List.of(ship, ship2)));
    assertEquals(1, shotReport.getNumShips());
  }
}