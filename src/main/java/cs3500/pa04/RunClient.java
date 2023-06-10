package cs3500.pa04;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShotReport;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.net.Socket;

public class RunClient {

  public void run(String host, int port) throws IOException {
    ShotReport shotReport = new ShotReport();
    Player player = new AiPlayer(shotReport);

    Socket server = new Socket(host, port);
    ProxyController proxyController = new ProxyController(server, player);
    proxyController.run();
  }
}
