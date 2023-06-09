package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;
import cs3500.pa04.ShipAdapter;
import java.util.List;

public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> fleet) {

}
