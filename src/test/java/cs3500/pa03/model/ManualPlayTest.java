package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ManualPlayTest {

  @Test
  void name() {
    ManualPlay player = new ManualPlay("Foo", null, null);
    assertEquals("Foo", player.name());
  }

  @Test
  void setup() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    ShotReport shotReport = new ShotReport();
    ManualPlay player = new ManualPlay(null, shotReport, null);
    player.setup(6, 6, numShips);
    assertEquals(6, shotReport.getFleet().size());

  }

  @Test
  void checkExceptionSetUp() {
    HashMap<ShipType, Integer> map = new HashMap<>();
    map.put(ShipType.CARRIER, 4);
    map.put(ShipType.SUBMARINE, 2);
    map.put(ShipType.DESTROYER, 3);
    map.put(ShipType.BATTLESHIP, 10);
    ManualPlay mp = new ManualPlay(null, null, null);
    assertThrows(IllegalArgumentException.class, () -> mp.setup(8, 8, map));
  }

  @Test
  void takeShots() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    ShotReport shotReport = new ShotReport();
    ManualPlay player = new ManualPlay(null, shotReport, null);
    player.setup(15, 15, numShips);

    Coord shot1 = new Coord(0, 2);
    Coord shot2 = new Coord(1, 1);
    Coord shot3 = new Coord(1, 2);
    ArrayList<Coord> shots = new ArrayList<>(Arrays.asList(shot1, shot2, shot3));
    shotReport.launchMissiles(shots);

    assertEquals(shots, player.takeShots());
  }

  @Test
  void reportDamage() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    ManualPlay player = new ManualPlay(null, new ShotReport(), null);
    ArrayList<Ship> ships = player.setup(15, 15, numShips);
    Coord coord1 = ships.get(0).getCoords().get(0);
    Coord coord2 = ships.get(1).getCoords().get(0);
    ArrayList<Coord> expected = new ArrayList<>(Arrays.asList(coord1, coord2));
    ArrayList<Coord> damageShots = new ArrayList<>(expected);
    damageShots.add(this.findUnusedCoord(ships));

    assertEquals(expected, player.reportDamage(damageShots));
  }

  private Coord findUnusedCoord(ArrayList<Ship> ships) {
    ArrayList<Coord> allShipSegments = new ArrayList<>();
    for (Ship ship : ships) {
      allShipSegments.addAll(ship.getCoords());
    }
    Random random = new Random();
    int x = 14;
    int y = 14;
    while (allShipSegments.contains(new Coord(x, y))) {
      x = random.nextInt(15);
      y = random.nextInt(15);
    }
    return new Coord(x, y);
  }

  @Test
  void successfulHits() {
    GameEventReport ger = new GameEventReport();
    assertNull(ger.getMessage());

    ManualPlay player = new ManualPlay(null, null, ger);
    Coord shot1 = new Coord(0, 2);
    Coord shot2 = new Coord(1, 1);
    Coord shot3 = new Coord(1, 2);
    ArrayList<Coord> shots = new ArrayList<>(Arrays.asList(shot1, shot2, shot3));
    player.successfulHits(shots);
    String expected = """
        (0, 2) was a Hit!
        (1, 1) was a Hit!
        (1, 2) was a Hit!
        """;
    assertEquals(expected, ger.getMessage());

  }

  @Test
  void endGame() {
    GameEventReport ger = new GameEventReport();
    assertNull(ger.getMessage());
    assertNull(ger.getGameResult());

    ManualPlay player = new ManualPlay(null, null, ger);
    player.endGame(GameResult.WIN, "You sunk all their Battleships!");

    String expected = "You sunk all their Battleships!";
    assertEquals(expected, ger.getMessage());
    assertEquals(GameResult.WIN, ger.getGameResult());

  }
}