package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.DisplaySalvo;
import cs3500.pa03.view.ViewSalvo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class RunSalvoTest {

  @Test
  void intro() {

    String dimensions = "20 8 2 8 8 20 8 2 10 8";
    ByteArrayInputStream bais = new ByteArrayInputStream(dimensions.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);
    runSalvo.intro();

    String expected = """
        Welcome to BattleSalvo!
        Please enter a height and width between 6 and 15 inclusive
        -->The dimensions you entered were invalid.
        Please only enter dimensions between 6 and 15 inclusive
        -->""";
    assertTrue(baos.toString().contains(expected));
  }

  @Test
  void fleetSelection() {
    String fleetInput = "8 8 1 1 1 1";
    ByteArrayInputStream bais = new ByteArrayInputStream(fleetInput.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);
    runSalvo.intro();
    runSalvo.fleetSelection();

    String expected1 = "Welcome to BattleSalvo!";
    String expected2 = "Please enter an integer for each type of ship.";
    String expected3 = "The maximum number of ships in your fleet is";
    assertTrue(baos.toString().contains(expected1));
    assertTrue(baos.toString().contains(expected2));
    assertTrue(baos.toString().contains(expected3));
  }

  @Test
  void fleetErrorTest() {
    String expected1 = "There must be at least one of every ship and you may not have more than";
    String fleetErrorInput = "8 8 0 0 0 0 1 0 1 1 1 1 0 1 1 1 1 0 0 0 0 0 20 2 1 1 2 20 1 1"
        + " 1 2 20 4 1 2 3 20 2 1 1 2";

    ByteArrayInputStream bais = new ByteArrayInputStream(fleetErrorInput.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);
    runSalvo.intro();
    runSalvo.fleetSelection();

    assertTrue(baos.toString().contains(expected1));
  }

  @Test
  void runSalvoTestFirstLoop() {
    String input = "6 6 1 1 1 1 0 4\n0 1\n1 1\n0 2";
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);

    runSalvo.intro();
    runSalvo.fleetSelection();
    assertThrows(NoSuchElementException.class, runSalvo::runSalvo);


  }

  @Test
  void runSalvoDetectWinner() {
    String input = this.getAllShotsString();
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);

    runSalvo.intro();
    runSalvo.fleetSelection();
    runSalvo.runSalvo();
    //System.out.println(baos);
    String expected1 = "Your Board:";
    String expected2 = "Please enter";
    String expected3 = "You sunk all your opponent's ships!";
    String expected3Tie = "Both player's ships are sunk!";
    String expected3Lose = "Your opponent sunk your ships!";
    assertTrue(baos.toString().contains(expected1));
    assertTrue(baos.toString().contains(expected2));
    assertTrue(baos.toString().contains(expected3)
        || baos.toString().contains(expected3Tie)
        || baos.toString().contains(expected3Lose));
  }

  private String getAllShotsString() {
    return """
        6 6 1 1 1 1 0 0
        0 1
        0 2
        0 3
        0 4
        0 5
        1 0
        1 1
        1 2
        1 3
        1 4
        1 5
        2 0
        2 1
        2 2
        2 3
        2 4
        2 5
        3 0
        3 1
        3 2
        3 3
        3 4
        3 5
        4 0
        4 1
        4 2
        4 3
        4 4
        4 5
        5 0
        5 1
        5 2
        5 3
        5 4
        5 5
        """;
  }

  private String getAllShotsStringWithInvalid() {
    return """
        6 6 1 1 1 1
        0 0
        0 20
        20 0
        -1 0
        0 -1
        0 0
        0 1
        0 2
        0 3
        0 4
        0 5
        1 0
        1 1
        1 2
        1 3
        1 4
        1 5
        2 0
        2 1
        2 2
        2 3
        2 4
        2 5
        3 0
        3 1
        3 2
        3 3
        3 4
        3 5
        4 0
        4 1
        4 2
        4 3
        4 4
        4 5
        5 0
        5 1
        5 2
        5 3
        5 4
        5 5
        
        """;
  }

  @Test
  void runSalvoInvalidSalvo() {
    String input = this.getAllShotsStringWithInvalid();
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);

    runSalvo.intro();
    runSalvo.fleetSelection();
    runSalvo.runSalvo();
    //System.out.println(baos);
    String expected1 = "Your Board:";
    String expected2 = "Please enter";
    String expectedInvalid = "ome value you entered is invalid";
    assertTrue(baos.toString().contains(expected1));
    assertTrue(baos.toString().contains(expected2));
    assertTrue(baos.toString().contains(expectedInvalid));
    String expected3Tie = "Both player's ships are sunk!";
    String expected3Lose = "Your opponent sunk your ships!";
    String expected3 = "You sunk all your opponent's ships!";
    assertTrue(baos.toString().contains(expected3)
        || baos.toString().contains(expected3Tie)
        || baos.toString().contains(expected3Lose));
  }

  @Test
  void isGameOver() {
    String input = "8 8";
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);

    assertTrue(runSalvo.isGameOver());

  }
}