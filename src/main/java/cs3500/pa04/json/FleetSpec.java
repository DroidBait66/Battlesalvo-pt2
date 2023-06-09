package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FleetSpec(
    @JsonProperty("CARRIER") int carriers,
    @JsonProperty("BATTLESHIP") int battleships,
    @JsonProperty("DESTROYER") int destroyers,
    @JsonProperty("SUBMARINE") int submarines) {

}
