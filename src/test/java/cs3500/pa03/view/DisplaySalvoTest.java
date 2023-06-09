package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameBoard;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisplaySalvoTest {
  private ByteArrayOutputStream baos = new ByteArrayOutputStream();
  private DisplaySalvo display = new DisplaySalvo(baos);

  @BeforeEach
  void setUp() {
    baos = new ByteArrayOutputStream();
    display = new DisplaySalvo(baos);
  }

  @Test
  void displayWelcome() {
    String expected = """
      Welcome to BattleSalvo!
      Please enter a height and width between 6 and 15 inclusive
      -->""";
    display.displayWelcome();
    assertEquals(expected, baos.toString());
  }

  @Test
  void checkInvalidDimensions() {
    String expected = """
        The dimensions you entered were invalid.
        Please only enter dimensions between 6 and 15 inclusive
        -->""";
    display.displayInvalidDimensions();
    assertEquals(expected, baos.toString());
  }

  @Test
  void displayFleetSelection() {
    String expected =  """
        Please enter an integer for each type of ship.
        Your fleet must contain at least one of each
        type of the following ships: Carrier, Battleship, Destroyer, Submarine
        The maximum number of ships in your fleet is\s""" + 7 + """
        
        -->""";
    display.displayFleetSelection(7);
    assertEquals(expected, baos.toString());
  }

  @Test
  void displayInvalidFleet() {
    String expect =  """
        There must be at least one of every ship and you may not have more than 10"""
        + """ 
         Please enter an integer for each of the following ship types:
         Carrier, Battleship, Destroyer, Submarine
         -->""";
    display.displayInvalidFleet(10);
    assertEquals(expect, baos.toString());
  }

  @Test
  void displayGame() {
    GameBoard gameBoardTest = new GameBoard(8, 8);
    Coord coord1 = new Coord(0, 4);
    Coord coord2 = new Coord(1, 4);
    Coord coord3 = new Coord(2, 4);
    Ship ship1 = new Ship(ShipType.SUBMARINE,
        new ArrayList<>(Arrays.asList(coord1, coord2, coord3)));
    gameBoardTest.setUp(ship1);

    display.displayGame(gameBoardTest, new GameBoard(8, 8));

    String expected = """
        Opponent's Board:
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
                
        Your Board:
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        S S S - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        - - - - - - - -\s
        """;
    assertEquals(expected, baos.toString());
  }

  @Test
  void askForMissileLocations() {
    String expected = "\nPlease enter 13 Shots:\n-->";
    display.askForMissileLocations(13);
    assertEquals(expected, baos.toString());
  }

  @Test
  void displayEndOfGame() {
    String expected = "The game has ended in a DRAW\nBecause both players sunk all ships\n";
    display.displayEndOfGame(GameResult.DRAW, "both players sunk all ships");
    assertEquals(expected, baos.toString());
  }
}