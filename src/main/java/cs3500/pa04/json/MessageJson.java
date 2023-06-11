package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The format of a message in JSON to/from the server starting with the name of the method and then
 *the method's arguments
 *
 * @param methodName String representing the specific method name being communicated
 * @param arguments the JsonNode object of information being passed to/from the server/player
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments")JsonNode arguments) {


}

