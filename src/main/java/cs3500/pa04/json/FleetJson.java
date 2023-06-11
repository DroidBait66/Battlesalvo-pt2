package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.ShipAdapter;
import java.util.List;

/**
 * Represents a player's fleet of ships
 *
 * @param fleet a list of ship adapters that represents a player's fleet
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> fleet) {

}
