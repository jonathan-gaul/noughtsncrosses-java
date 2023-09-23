package tech.gaul.noughtsncrosses.web.api.services;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import tech.gaul.noughtsncrosses.logic.Game;

import java.nio.ByteBuffer;
import java.util.*;

@Service
@ApplicationScope
public class GameServiceImpl implements GameService {

    private static final Base64.Encoder BASE64_URL_ENCODER = Base64.getUrlEncoder().withoutPadding();

    private static final Map<String, Game> games = new HashMap<>();

    /**
     * Get a game by its key.
     * @param key The key to look up.
     * @return The game with the given key or null if no game was found.
     */
    public Game get(String key) {
        return games.get(key);
    }

    /**
     * Create a new Game.
     * @return The new game's Key.
     */
    public String createGame() {
        var game = new Game();

        // Make a 22-char key from a GUID.
        var uuid = UUID.randomUUID();
        var buffer = ByteBuffer.wrap(new byte[16]);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        var bytes = buffer.array();

        var key = BASE64_URL_ENCODER.encodeToString(bytes);

        games.put(key, game);
        return key;
    }

    public Set<String> keys() {
        return games.keySet();
    }

}
