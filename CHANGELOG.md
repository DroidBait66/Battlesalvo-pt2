Changes from PA03 to PA04

- Abstracted setup and report damage methods in the ManualPlay and AiPlayer implementations into an Abstract Class that
  implements the Player interface
  - Abstraction used to reduce the code duplication between AiPlayer and ManualPlay. Now both classes simply extend the 
    abstract class and have access to the same method

- The Driver now checks for Command Line Arguments and initializes either a BattleSalvo object like before or a 
  RunClient object to run BattleSalvo against the server at the host and port given in the Command Line Arguments
    - Driver needed to be updated in order to create a ProxyController and connect to the server

- Changed the ControlSalvo interface to only have a single method (run()) instead of the original four. Updated those
  tests using a "@BeforeEach" to reduce amount of code duplication
  - Updated controller because there was no reason for the BattleSalvo class to call four methods in a row without any
    change in inputs or their values. Easier to simply call a single method that performs those same actions in the same
    order.

- Added Jackson JSON tags to Coord class as a means to make Coords convert to JSON without creating a new Record class
  - A JSON record to create Coords was necessary to communicate to the server, so adding functionality to the Coord
    class seemed more reasonable than to create a nearly identical Record for the same function.