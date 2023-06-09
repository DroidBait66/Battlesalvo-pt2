package cs3500.pa04.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.controller.ControlSalvo;
import cs3500.pa03.model.Player;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

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

    if (methodName.equals("join")) {
      this.handleJoin();
    } else if (methodName.equals("setup")) {
      this.handleSetUp(arguments);
    } else if (methodName.equals("take-shots")) {
      this.handleTakeShots();
    } else if (methodName.equals("report-damage")) {
      this.handleReportDamage(arguments);
    } else if (methodName.equals("successful-hits")) {
      this.handleSuccessfulHits(arguments);
    } else if (methodName.equals("end-game")) {
      this.handleEndGame(arguments);
    } else {
      throw new IllegalArgumentException("Method name not supported");
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

  private void handleSetUp(JsonNode args) {

  }

  private void handleTakeShots() {

  }

  private void handleReportDamage(JsonNode args) {

  }

  private void handleSuccessfulHits(JsonNode args) {

  }

  private void handleEndGame(JsonNode args) {

  }


}
