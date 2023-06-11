package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * Represents a volley of shots to be converted into JSON format
 *
 * @param shots a list of coordinates representing a salvo
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") List<Coord> shots) {


}
