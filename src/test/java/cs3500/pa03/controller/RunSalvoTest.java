package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.view.DisplaySalvo;
import cs3500.pa03.view.ViewSalvo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class RunSalvoTest {

  @Test
  void introErrors() {

    String dimensions = "0 7 7 0 20 7 7 20 " + getAllShotsString();
    ByteArrayInputStream bais = new ByteArrayInputStream(dimensions.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);
    runSalvo.run();

    String expected1 = "Welcome to BattleSalvo!";
    String expected2 = "The dimensions you entered were invalid.";
    assertTrue(baos.toString().contains(expected1));
    assertTrue(baos.toString().contains(expected2));
  }

  @Test
  void fleetErrorTest() {
    String expected1 = "There must be at least one of every ship and you may not have more than";
    String fleetErrorInput = "8 8 0 0 0 0 1 0 1 1 1 1 0 1 1 1 1 0 0 0 0 0 20 2 1 1 2 20 1 1"
        + " 1 2 20 4 1 2 3 20 2 1 1 2" + this.getAllShotsString().substring(11);

    ByteArrayInputStream bais = new ByteArrayInputStream(fleetErrorInput.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);
    runSalvo.run();

    assertTrue(baos.toString().contains(expected1));
  }

  @Test
  void runSalvoDetectWinner() {
    String input = this.getAllShotsString();
    ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ViewSalvo view = new DisplaySalvo(baos);
    RunSalvo runSalvo = new RunSalvo(view, bais);

    runSalvo.run();

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

    runSalvo.run();

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


}

