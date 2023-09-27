package tech.gaul.noughtsncrosses.web.api.services;

import tech.gaul.noughtsncrosses.logic.Game;
import tech.gaul.noughtsncrosses.web.api.dto.GameDTO;

import java.util.Set;

public interface GameService {
    /**
     * Get a game by its key.
     * @param key The key to look up.
     * @return The game with the given key or null if no game was found.
     */
    Game get(String key);

    /**
     * Create a new Game.
     * @return The new game's Key.
     */
    String createGame();

    /**
     * Gets keys for known games.
     * @return A Set of keys representing the currently known keys.
     */
    Set<String> keys();

    /**
     * Send a game update to WebSocket subscribers.
     */
    void sendGameUpdate(GameDTO game);
}
