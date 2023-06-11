package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the numbers of each type of ship in the game
 *
 * @param carriers the number of carriers the game will have for each player
 * @param battleships the number of battleships the game will have for each player
 * @param destroyers the number of destroyers the game will have for each player
 * @param submarines the number of submarines the game will have for each player
 */
public record FleetSpecJson(
    @JsonProperty("CARRIER") int carriers,
    @JsonProperty("BATTLESHIP") int battleships,
    @JsonProperty("DESTROYER") int destroyers,
    @JsonProperty("SUBMARINE") int submarines) {

}
