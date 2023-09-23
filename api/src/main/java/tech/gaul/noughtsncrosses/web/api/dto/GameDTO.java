package tech.gaul.noughtsncrosses.web.api.dto;

import tech.gaul.noughtsncrosses.logic.Game;
import tech.gaul.noughtsncrosses.logic.Piece;

public class GameDTO {

    public String Key;
    public int Turn;
    public int GridSize;
    public String[] Grid;

    public GameDTO(String key, Game game) {
        Key = key;
        Turn = game.turn();
        GridSize = game.grid().size();
        Grid =
            game.grid().cells()
                .stream()
                .map(c -> c.getPiece() == null ? " " :
                          c.getPiece() == Piece.X ? "X" : "O")
                .toArray(String[]::new);
    }

}
