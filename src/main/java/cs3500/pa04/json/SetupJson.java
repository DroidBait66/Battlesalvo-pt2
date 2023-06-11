package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Represents the arguments sent from the server to the player to set up a game of BattleSalvo
 *
 * @param width int representing the width of the BattleSalvo board
 * @param height int representing the height of the BattleSalvo board
 * @param fleetSpec a JsonNode object that contains the number of each ShipTypes
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") JsonNode fleetSpec) {

}
