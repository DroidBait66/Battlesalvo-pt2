package cs3500.pa04;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShotReport;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.net.Socket;

/**
 * Class representing the AiPlayer playing against a server with the given host and port
 */
public class RunClient {

  /**
   * Initiates a game of BattleSalvo using the ProxyController between the server at the given host
   *and port and the AiPlayer
   *
   * @param host host of the given server
   * @param port port of the given server
   * @throws IOException if an error occurs when connecting to the server
   */
  public void run(String host, int port) throws IOException {
    ShotReport shotReport = new ShotReport();
    Player player = new AiPlayer(shotReport);

    Socket server = new Socket(host, port);
    ProxyController proxyController = new ProxyController(server, player);
    proxyController.run();
  }
}
