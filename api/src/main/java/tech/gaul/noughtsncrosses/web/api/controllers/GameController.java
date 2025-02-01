package tech.gaul.noughtsncrosses.web.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;
import tech.gaul.noughtsncrosses.logic.Piece;
import tech.gaul.noughtsncrosses.web.api.dto.GameDTO;
import tech.gaul.noughtsncrosses.web.api.dto.PlayDTO;
import tech.gaul.noughtsncrosses.web.api.services.GameService;

import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games/{key}")
    public GameDTO create(@PathVariable(required = false, name = "key") String key) {

        // Create a new game.
        var newKey = gameService.createGame();
        var newGame = new GameDTO(newKey, gameService.get(newKey));

        // If we have an existing key, tell everyone subscribed to the existing key that we're switching to a new game.
        if (key != null) {
            gameService.sendGameUpdate(key, newGame);
        }

        return newGame;
    }

    @GetMapping("/games")
    public Set<String> count() {
        return gameService.keys();
    }

    @SubscribeMapping("/games/changes/{key}")
    public void subscribe(@DestinationVariable String key) {
        System.out.println("Client subscribed to updates for game " + key);
    }

    @GetMapping("/games/{key}")
    public ResponseEntity<GameDTO> get(@PathVariable("key") String key) {
        var game = gameService.get(key);
        if (game == null) return notFound();

        return new ResponseEntity<>(new GameDTO(key, game), HttpStatus.OK);
    }

    @PutMapping(path = "/games/{key}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameDTO> place(@PathVariable("key") String key, @RequestBody PlayDTO play) {
        var game = gameService.get(key);
        if (game == null) return notFound();

        var dto = new GameDTO(key, game);

        var piece = Piece.valueOf(play.Piece);
        if (piece != game.getCurrentPiece())
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);

        if (!game.grid().cell(play.Row, play.Column).setPiece(piece))
            return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);

        dto = new GameDTO(key, game); // Must update DTO as we've played a piece...
        gameService.sendGameUpdate(dto.Key, dto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<>((T)null, HttpStatus.NOT_FOUND);
    }
}
