package boardgame.json;

import utils.FileSystemRepository;

import java.io.File;
import java.io.IOException;
import java.util.Set;

/**
 * Repository class for the {@link GameResult} entity.
 */
public class GameResultRepository extends FileSystemRepository<GameResult> {

    private static final File LEADERBOARD_FILE = new File(System.getProperty("user.home")
            +File.separator+"leaderboard.json");


    public GameResultRepository() {
        super(GameResult.class);
        try {
            this.loadFromFile(LEADERBOARD_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<GameResult> addOne(GameResult element) {
        if (element.getId() == null) {
            element.setId(getAll().stream()
                    .mapToLong(GameResult::getId)
                    .max()
                    .orElse(0L) + 1L);
        }

        return super.addOne(element);
    }

    public File getLeaderboardFile() {
        return LEADERBOARD_FILE;
    }
}

