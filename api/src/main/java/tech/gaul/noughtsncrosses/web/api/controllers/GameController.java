package tech.gaul.noughtsncrosses.web.api.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.gaul.noughtsncrosses.logic.Piece;
import tech.gaul.noughtsncrosses.web.api.dto.GameDTO;
import tech.gaul.noughtsncrosses.web.api.dto.PlayDTO;
import tech.gaul.noughtsncrosses.web.api.services.GameService;

@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public GameDTO create() {
        // Create a new game.
        var key = gameService.createGame();
        return new GameDTO(key, gameService.get(key));
    }

    @GetMapping("/games/:key")
    public ResponseEntity<GameDTO> get(@PathParam("key") String key) {
        var game = gameService.get(key);
        if (game == null) return notFound();

        return new ResponseEntity<>(new GameDTO(key, game), HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<>((T)null, HttpStatus.NOT_FOUND);
    }
}
