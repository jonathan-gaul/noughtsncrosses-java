package tech.gaul.noughtsncrosses.web.api.services;

import tech.gaul.noughtsncrosses.logic.Game;

public interface GameService {
    Game get(String key);
    String createGame();
}
