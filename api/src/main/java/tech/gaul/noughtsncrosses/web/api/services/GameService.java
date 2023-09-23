package tech.gaul.noughtsncrosses.web.api.services;

import tech.gaul.noughtsncrosses.logic.Game;

import java.util.Set;

public interface GameService {
    Game get(String key);
    String createGame();
    Set<String> keys();
}
