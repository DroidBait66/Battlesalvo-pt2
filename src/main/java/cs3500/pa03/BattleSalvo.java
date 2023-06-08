package cs3500.pa03;

import cs3500.pa03.controller.ControlSalvo;
import cs3500.pa03.controller.RunSalvo;
import cs3500.pa03.view.DisplaySalvo;
import cs3500.pa03.view.ViewSalvo;

/**
 * Wrapper class for the whole application to get the game started by instantiating MVC classes
 */
public class BattleSalvo {
  ViewSalvo view = new DisplaySalvo(System.out);
  ControlSalvo controller = new RunSalvo(view, System.in);

  /**
   * Starts the BattleSalvo game
   */
  public void run() {
    controller.run();
  }
}
