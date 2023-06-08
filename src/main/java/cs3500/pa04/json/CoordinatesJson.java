package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

public record CoordinatesJson(
    @JsonProperty("shots") List<Coord> shots) {


}
