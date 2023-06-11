package cs3500.pa04.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class of static methods to facilitate conversion from Java records to JSON objects
 */
public class JsonUtils {

  /**
   * Creates a JsonNode object from the given Record object
   *
   * @param record the JSON record to be converted into a JsonNode type object
   * @return JsonNode representation of the given record
   */
  public static JsonNode serializeRecord(Record record) {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.convertValue(record, JsonNode.class);

  }

}
