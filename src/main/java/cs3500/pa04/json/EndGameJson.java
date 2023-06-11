package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * Represents the end of a game to be converted to/from JSON
 *
 * @param gameResult the end result of the game
 * @param reason why the game ended in this way
 */
public record EndGameJson(
    @JsonProperty("result") GameResult gameResult,
    @JsonProperty("reason") String reason) {
}
