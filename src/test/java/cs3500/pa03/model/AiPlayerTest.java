package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  @Test
  void name() {
    AiPlayer aiPlayer = new AiPlayer(new ShotReport());
    assertEquals("computer", aiPlayer.name());
  }

  @Test
  void setup() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    ShotReport shotReport = new ShotReport();
    AiPlayer player = new AiPlayer(shotReport);
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
    AiPlayer ai = new AiPlayer(null);
    assertThrows(IllegalArgumentException.class, () -> ai.setup(8, 8, map));
  }

  @Test
  void takeShots() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    AiPlayer player = new AiPlayer(new ShotReport());
    player.setup(15, 15, numShips);
    assertEquals(6, player.takeShots().size());
  }

  @Test
  void reportDamage() {
    HashMap<ShipType, Integer> numShips = new HashMap<>();
    numShips.put(ShipType.CARRIER, 3);
    numShips.put(ShipType.BATTLESHIP, 1);
    numShips.put(ShipType.DESTROYER, 1);
    numShips.put(ShipType.SUBMARINE, 1);

    AiPlayer player = new AiPlayer(new ShotReport());
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
    while (allShipSegments.contains(new Coord(x,y))) {
      x = random.nextInt(15);
      y = random.nextInt(15);
    }
    return new Coord(x, y);
  }

  @Test
  void successfulHits() {
    ShotReport shotReport = new ShotReport();
    AiPlayer player = new AiPlayer(shotReport);
    assertEquals(new ArrayList<Coord>(), player.getShotsThatHitOpponent());
    Coord c1 = new Coord(0, 0);
    Coord c2 = new Coord(0, 1);
    ArrayList<Coord> expected = new ArrayList<>(Arrays.asList(c1, c2));

    player.successfulHits(expected);
    assertEquals(expected, player.getShotsThatHitOpponent());
  }

  @Test
  void endGame() {
    // this method does nothing -- testing for nothingness?
    ShotReport shotReport = new ShotReport();
    AiPlayer player = new AiPlayer(shotReport);
    player.endGame(GameResult.DRAW, "all ships destroyed");
    assertEquals(shotReport.getNumShips(), new ShotReport().getNumShips());
  }
}