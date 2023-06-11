package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the arguments (GitHub username and game type) to send to the server as JSON
 *
 * @param githubUsername the username of one of the programmers on GitHub
 * @param gameType either "SINGLE" or "MULTI"
 */
public record JoinJson(
    @JsonProperty("name") String githubUsername,
    @JsonProperty("game-type") String gameType) {
}
