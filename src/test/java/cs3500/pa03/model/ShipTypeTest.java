package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ShipTypeTest {

  @Test
  void getShipSize() {
    ShipType shipType1 = ShipType.CARRIER;
    assertEquals(6, shipType1.getShipSize());

    ShipType shipType2 = ShipType.BATTLESHIP;
    assertEquals(5, shipType2.getShipSize());
  }
}