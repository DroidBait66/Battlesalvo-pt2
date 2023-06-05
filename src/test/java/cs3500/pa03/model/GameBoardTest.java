package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {
  private int expectedDim = 7;
  private GameBoard gameBoardTest = new GameBoard(expectedDim, expectedDim);
  private Coord coord1 = new Coord(0, 0);
  private Coord coord2 = new Coord(1, 0);
  private Coord coord3 = new Coord(2, 0);
  private Ship ship1 = new Ship(ShipType.SUBMARINE,
      new ArrayList<>(Arrays.asList(coord1, coord2, coord3)));
  ArrayList<BoardCell> expectRow1 =
      new ArrayList<>(Arrays.asList(BoardCell.SHIP, BoardCell.SHIP, BoardCell.SHIP,
          BoardCell.EMPTY, BoardCell.EMPTY, BoardCell.EMPTY, BoardCell.EMPTY));

  @BeforeEach
  void setUpTests() {
    expectedDim = 7;
    gameBoardTest = new GameBoard(expectedDim, expectedDim);
    coord1 = new Coord(0, 0);
    coord2 = new Coord(1, 0);
    coord3 = new Coord(2, 0);
    ship1 = new Ship(ShipType.SUBMARINE,
        new ArrayList<>(Arrays.asList(coord1, coord2, coord3)));
  }

  @Test
  void checkInitBoard() {
    GameBoard gameBoard = new GameBoard(6, 6);
    int expectedDimension = 6;
    assertEquals(expectedDimension, gameBoard.getBoard().size());
    assertEquals(expectedDimension, gameBoard.getBoard().get(0).size());
  }

  @Test
  void checkConstructorExceptions() {
    assertThrows(IllegalArgumentException.class, () -> new GameBoard(4, 4));
    assertThrows(IllegalArgumentException.class, () -> new GameBoard(16, 16));
    assertThrows(IllegalArgumentException.class, () -> new GameBoard(7, 20));
    assertThrows(IllegalArgumentException.class, () -> new GameBoard(2, 10));
  }

  @Test
  void checkGetBoard() {
    GameBoard gameBoard = new GameBoard(15, 15);
    int expectedDimension = 15;
    assertEquals(expectedDimension, gameBoard.getBoard().size());
    assertEquals(expectedDimension, gameBoard.getBoard().get(0).size());
  }

  @Test
  void getBoardForOpponent() {
    gameBoardTest.setUp(ship1);


    assertEquals(expectedDim, gameBoardTest.getBoard().size());
    assertEquals(expectedDim, gameBoardTest.getBoard().get(0).size());
    assertEquals(expectRow1, gameBoardTest.getBoard().get(0));

    ArrayList<BoardCell> expectedAfterRemoval =
        new ArrayList<>(Arrays.asList(BoardCell.EMPTY, BoardCell.EMPTY, BoardCell.EMPTY,
            BoardCell.EMPTY, BoardCell.EMPTY, BoardCell.EMPTY, BoardCell.EMPTY));

    assertEquals(expectedDim, gameBoardTest.getBoardForOpponent().size());
    assertEquals(expectedDim, gameBoardTest.getBoardForOpponent().get(0).size());
    assertEquals(expectedAfterRemoval, gameBoardTest.getBoardForOpponent().get(0));

  }

  @Test
  void setUp() {
    Coord coord1 = new Coord(0, 1);
    Coord coord2 = new Coord(1, 1);
    Coord coord3 = new Coord(2, 1);
    Ship ship2 = new Ship(ShipType.SUBMARINE,
        new ArrayList<>(Arrays.asList(coord1, coord2, coord3)));

    gameBoardTest.setUp(ship1);
    assertEquals(expectRow1, gameBoardTest.getBoard().get(0));
    gameBoardTest.setUp(ship2);
    assertEquals(expectRow1, gameBoardTest.getBoard().get(1));
  }

  @Test
  void updateFromSalvo() {
    gameBoardTest.setUp(ship1);

    Coord shot1 = new Coord(0, 0);
    Coord shot2 = new Coord(6, 6);
    ArrayList<Coord> shots = new ArrayList<>(Arrays.asList(shot1, shot2));

    gameBoardTest.updateFromSalvo(shots);
    assertEquals(BoardCell.HIT, gameBoardTest.getBoard().get(0).get(0));
    assertEquals(BoardCell.MISS, gameBoardTest.getBoard().get(6).get(6));
  }

  @Test
  void checkGetEmptySpaces() {
    assertEquals(Math.pow(expectedDim, 2), gameBoardTest.getEmptySpaces());
    gameBoardTest.setUp(ship1);

    Coord shot1 = new Coord(0, 0);
    Coord shot2 = new Coord(6, 6);
    ArrayList<Coord> shots = new ArrayList<>(Arrays.asList(shot1, shot2));

    gameBoardTest.updateFromSalvo(shots);
    assertEquals(Math.pow(expectedDim, 2) - 2, gameBoardTest.getEmptySpaces());
  }
}