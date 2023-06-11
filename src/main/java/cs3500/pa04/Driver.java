package cs3500.pa04;

import cs3500.pa03.BattleSalvo;
import java.io.IOException;

/**
 * This is the main driver of this project.
 */
public class Driver {

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    /*
     * if no command line args, battleSalvo should function exactly like pa03
     *
     * WIth user inputs, the AiPlayer should play against the server
     * abstract the methods in controller into proxy controller, so regular controller
     * can still act as normal
     */
    if (args.length == 0) {
      // Run BattleSalvo like PA03 with a console player
      BattleSalvo battleSalvo = new BattleSalvo();
      battleSalvo.run();
    } else {

      String host = args[0];
      int port = Integer.parseInt(args[1]);
      // run the proxy controller
      RunClient runClient = new RunClient();
      try {
        runClient.run(host, port);
      } catch (IOException e) {
        System.out.println("An Error occurred while connecting to the server");
      }
    }



  }
}