package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.ShotReport;
import cs3500.pa04.controller.ProxyController;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

  private ByteArrayOutputStream output;
  private ProxyController controller;

  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void reset() {
    this.output = new ByteArrayOutputStream(2048);
    //assertEquals("", );
  }


  @Test
  void testJoin() {
    JsonNode joinMethod = createJsonMessage("join", null);
    MocketTest server = new MocketTest(this.output, List.of(joinMethod.toString()));

    try {
      controller = new ProxyController(server, new AiPlayer(new ShotReport()));
    } catch (IOException e) {
      // test fails if controller can't connect to the mocket
      fail();
    }

    controller.run();
    controllerResponse(JoinJson.class);

  }

  @Test
  void testSetUp() {
    FleetSpecJson fleetSpecJson =
        new FleetSpecJson(2, 1, 1, 2);
    JsonNode fleetSpecNode = JsonUtils.serializeRecord(fleetSpecJson);
    SetupJson setupJson = new SetupJson(6, 9, fleetSpecNode);
    JsonNode setupMethod = createJsonMessage("setup", setupJson);
    MocketTest server = new MocketTest(this.output, List.of(setupMethod.toString()));

    try {
      controller = new ProxyController(server, new AiPlayer(new ShotReport()));
    } catch (IOException e) {
      fail();
    }

    controller.run();
    controllerResponse(FleetJson.class);
  }

  @Test
  void testTakeShots() {
    JsonNode takeShotsMethod = createJsonMessage("take-shots", null);
    MocketTest server = new MocketTest(this.output, List.of(takeShotsMethod.toString()));

    try {
      controller = new ProxyController(server, new AiPlayer(new ShotReport()));
    } catch (IOException e) {
      fail();
    }

    controller.run();
    controllerResponse(CoordinatesJson.class);
  }

  @Test
  void testReportDamage() {
    Coord c1 = new Coord(1, 1);
    Coord c2 = new Coord(2, 2);
    CoordinatesJson coordinatesJson = new CoordinatesJson(List.of(c1, c2));
    JsonNode reportDamageMethod = createJsonMessage("report-damage", coordinatesJson);
    MocketTest server = new MocketTest(this.output, List.of(reportDamageMethod.toString()));

    try {
      HashMap<ShipType, Integer> shipNums = new HashMap<>();
      shipNums.put(ShipType.CARRIER, 1);
      shipNums.put(ShipType.BATTLESHIP, 1);
      shipNums.put(ShipType.DESTROYER, 1);
      shipNums.put(ShipType.SUBMARINE, 1);
      Player player = new AiPlayer(new ShotReport());
      player.setup(6, 6, shipNums);
      controller = new ProxyController(server, player);
    } catch (IOException e) {
      fail();
    }

    controller.run();
    controllerResponse(CoordinatesJson.class);
  }

  @Test
  void testSuccessfulHits() {
    Coord c1 = new Coord(1, 1);
    Coord c2 = new Coord(2, 2);
    CoordinatesJson coordinatesJson = new CoordinatesJson(List.of(c1, c2));
    JsonNode successfulHitsMethod = createJsonMessage("successful-hits",
        coordinatesJson);
    MocketTest server = new MocketTest(this.output, List.of(successfulHitsMethod.toString()));

    try {
      controller = new ProxyController(server, new AiPlayer(new ShotReport()));
    } catch (IOException e) {
      fail();
    }

    controller.run();
    controllerResponse(MessageJson.class);
  }

  @Test
  void testEndGame() {
    GameResult gameResult = GameResult.DRAW;
    String reason = "All ships have sunk!";
    EndGameJson endGameJson = new EndGameJson(gameResult, reason);
    JsonNode endGameMethod = createJsonMessage("end-game", endGameJson);
    MocketTest server = new MocketTest(this.output, List.of(endGameMethod.toString()));

    try {
      controller = new ProxyController(server, new AiPlayer(new ShotReport()));
    } catch (IOException e) {
      fail();
    }

    controller.run();
    controllerResponse(MessageJson.class);
  }

  private <T> void controllerResponse(Class<T> jsonRecord) {
    try {
      String outputString = removeMethodName(this.output.toString(StandardCharsets.UTF_8));
      JsonParser parser = new ObjectMapper().createParser(outputString);
      parser.readValueAs(jsonRecord);
    } catch (IOException e) {
      fail();
    }
  }

  private String removeMethodName(String entireOutput) {
    int idxSecondOpenCurlyBrace = entireOutput.indexOf("{", 2);
    if (idxSecondOpenCurlyBrace < 0) {
      return entireOutput;
    } else {
      return entireOutput.substring(idxSecondOpenCurlyBrace);
    }
  }


  private JsonNode createJsonMessage(String methodName, Record record) {
    MessageJson messageJson = new MessageJson(methodName, JsonUtils.serializeRecord(record));
    return JsonUtils.serializeRecord(messageJson);
  }

}