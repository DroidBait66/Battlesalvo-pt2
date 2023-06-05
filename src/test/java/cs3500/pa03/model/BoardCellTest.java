package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BoardCellTest {

  @Test
  void getMarker() {
    BoardCell cell1 = BoardCell.SHIP;
    assertEquals("S ", cell1.getMarker());

    BoardCell cell2 = BoardCell.EMPTY;
    assertEquals("- ", cell2.getMarker());
  }
}