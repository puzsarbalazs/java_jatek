package boardgame.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Class for representing an element of the leaderboard.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {

    private Long id;

    /**
     * Date of the game.
     */
    private ZonedDateTime created;

    /**
     * Name of the red player.
     */
    private String winner;

    /**
     * The amount of steps it took for the winner to win.
     */
    private Integer steps;
}
