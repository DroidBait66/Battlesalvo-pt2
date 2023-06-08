package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa04.json.CoordinatesJson;
import cs3500.pa04.json.JsonUtils;
import java.util.List;
import org.junit.jupiter.api.Test;

class JsonUtilsTest {

  @Test
  void serializeRecord() {
    Record record = new CoordinatesJson(List.of(new Coord(1, 1),
        new Coord(1, 2)));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode expected = objectMapper.convertValue(record, JsonNode.class);
    JsonUtils jsonUtils = new JsonUtils();
    JsonNode result = JsonUtils.serializeRecord(record);

    assertEquals(expected, result);
  }
}