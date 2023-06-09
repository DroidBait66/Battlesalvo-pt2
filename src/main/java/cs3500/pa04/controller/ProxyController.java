package cs3500.pa04.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.ControlSalvo;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.ShipAdapter;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controls a game between an external server and a player object
 */
public class ProxyController implements ControlSalvo {

  private final Socket server;
  private final InputStream input;
  private final PrintStream output;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Creates a new ProxyController with the given arguments
   *
   * @param server a socket that connects to the server which is being played against
   * @param player a Player object that will be playing in the server
   * @throws IOException if the input and/or output streams are not accessible
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.player = player;
    this.input = server.getInputStream();
    this.output = new PrintStream(server.getOutputStream());
  }

  /**
   * Controls the game of BattleSalvo between the server and the Player object
   */
  @Override
  public void run() {

  }

  /**
   * Handles the different server requests and passes the arguments to the necessary method
   *
   * @param messageJson the record that stores the method name and necessary arguments
   */
  private void delegateMessage(MessageJson messageJson) {
    String methodName = messageJson.methodName();
    JsonNode arguments = messageJson.arguments();

    switch (methodName) {
      case "join" -> this.handleJoin();
      case "setup" -> this.handleSetUp(arguments);
      case "take-shots" -> this.handleTakeShots();
      case "report-damage" -> this.handleReportDamage(arguments);
      case "successful-hits" -> this.handleSuccessfulHits(arguments);
      case "end-game" -> this.handleEndGame(arguments);
      default -> throw new IllegalArgumentException("Method name not supported");
    }
  }

  /**
   * Sends the server our GitHub username and game type
   */
  private void handleJoin() {
    String username = "HgOwl";
    JoinJson join = new JoinJson(username, "SINGLE");
    JsonNode joinNode = JsonUtils.serializeRecord(join);
    MessageJson outputMessage = new MessageJson("join", joinNode);
    JsonNode outputJson = JsonUtils.serializeRecord(outputMessage);
    this.output.println(outputJson);
  }

  /**
   * Sends the server our setup fleet
   *
   * @param args the JsonNode object that holds the specific about board and fleet size
   */
  private void handleSetUp(JsonNode args) {
    SetupJson setup = mapper.convertValue(args, SetupJson.class);
    FleetSpecJson fleetSpec = mapper.convertValue(setup.fleetSpec(), FleetSpecJson.class);
    int height = setup.height();
    int width = setup.width();
    HashMap<ShipType, Integer> numShips = this.createShipMap(fleetSpec);

    // List of our ships -- needs to be converted to server's ship type
    List<Ship> ships = player.setup(height, width, numShips);

    // Ships in the form the server uses
    List<ShipAdapter> adaptedShips = convertShips(ships);

    // fleet in its specific Json Record
    FleetJson fleet = new FleetJson(adaptedShips);

    // Serializing and sending the response to the server
    JsonNode fleetResponse = JsonUtils.serializeRecord(fleet);
    MessageJson fleetResponseMessage = new MessageJson("setup", fleetResponse);
    JsonNode messageOutput = JsonUtils.serializeRecord(fleetResponseMessage);
    this.output.println(messageOutput);
  }

  /**
   * Creates a hashmap of the ships using inputs from the server
   *
   * @param fleetSpec FleetSpecJson given by the server, read previously with an object mapper
   * @return a HashMap of ShipType and Integer
   */
  private HashMap<ShipType, Integer> createShipMap(FleetSpecJson fleetSpec) {
    HashMap<ShipType, Integer> result = new HashMap<>();
    result.put(ShipType.CARRIER, fleetSpec.carriers());
    result.put(ShipType.BATTLESHIP, fleetSpec.battleships());
    result.put(ShipType.DESTROYER, fleetSpec.destroyers());
    result.put(ShipType.SUBMARINE, fleetSpec.submarines());
    return result;
  }

  /**
   * Converts them into the ship adapter, which is stored like the server
   *
   * @param ships the list of ships created from our setup method
   * @return a list of ShipAdapters that have the form the server uses
   */
  private List<ShipAdapter> convertShips(List<Ship> ships) {
    ArrayList<ShipAdapter> convertedShips = new ArrayList<>();
    for (Ship ship : ships) {
      ShipAdapter newShip = new ShipAdapter(ship);
      convertedShips.add(newShip);
    }
    return convertedShips;
  }

  /**
   * Gives the server our salvo
   */
  private void handleTakeShots() {
    // if we want to show the game as it progresses, this would be the place to call view display
    // game methods

    List<Coord> shots = player.takeShots();
    CoordinatesJson volleyJson = new CoordinatesJson(shots);
    JsonNode takeShotsResponse = JsonUtils.serializeRecord(volleyJson);
    MessageJson shotsMessageJson = new MessageJson("take-shots", takeShotsResponse);
    JsonNode takeShotsMessage = JsonUtils.serializeRecord(shotsMessageJson);
    this.output.println(takeShotsMessage);
  }

  /**
   * Takes in the opponents shots from the server and records what ships have been damaged
   *
   * @param args the JsonNode object that stores the location of the server player's shots
   */
  private void handleReportDamage(JsonNode args) {
    CoordinatesJson coordinatesJson = mapper.convertValue(args, CoordinatesJson.class);
    List<Coord> opponentsShots = coordinatesJson.shots();
    List<Coord> opponentHits = player.reportDamage(opponentsShots);

    CoordinatesJson oppHitsJson = new CoordinatesJson(opponentHits);
    JsonNode hitsResponse = JsonUtils.serializeRecord(oppHitsJson);
    MessageJson oppHitsMessage = new MessageJson("report-damage", hitsResponse);
    JsonNode reportDamageResponse = JsonUtils.serializeRecord(oppHitsMessage);
    this.output.println(reportDamageResponse);
  }

  private void handleSuccessfulHits(JsonNode args) {
    CoordinatesJson coordinatesJson = mapper.convertValue(args, CoordinatesJson.class);
    List<Coord> successfulHits = coordinatesJson.shots();
    player.successfulHits(successfulHits);

    MessageJson successfulHitsResponseMessage =
        new MessageJson("successful-hits", null);
    JsonNode responseJson = JsonUtils.serializeRecord(successfulHitsResponseMessage);
    this.output.println(responseJson);
  }

  private void handleEndGame(JsonNode args) {

  }
}
