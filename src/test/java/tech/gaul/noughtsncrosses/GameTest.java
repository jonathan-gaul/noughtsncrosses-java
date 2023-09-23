package tech.gaul.noughtsncrosses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {


    @Test
    void givenWinningMoveByX_whenGetWinner_thenReturnX() {

        var game = createWinningGame(Piece.X);

        Piece expectedValue = Piece.X;
        Piece actualValue = game.winner();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenWinningMoveByO_whenGetWinner_thenReturnO() {
        var game = createWinningGame(Piece.O);

        Piece expectedValue = Piece.O;
        Piece actualValue = game.winner();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenDrawingMove_whenGetWinner_thenReturnEmpty() {
        var game = createDrawingGame();

        Piece expectedValue = null;
        Piece actualValue = game.winner();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenWinningMove_whenIsFinished_thenReturnTrue() {
        var game = createDrawingGame();

        boolean expectedValue = true;
        boolean actualValue = game.isFinished();
        assertEquals(expectedValue, actualValue);
    }

    public Game createWinningGame(Piece winner) {
        var game = new Game();
        game.grid().cell(0, 0).setPiece(winner);
        game.grid().cell(0, 1).setPiece(winner);
        game.grid().cell(0, 2).setPiece(winner);
        return game;
    }

    public Game createDrawingGame() {
        var game = new Game();
        var grid = game.grid();

        grid.cell(0, 0).setPiece(Piece.X);
        grid.cell(0, 1).setPiece(Piece.X);
        grid.cell(0, 2).setPiece(Piece.O);

        grid.cell(1, 0).setPiece(Piece.O);
        grid.cell(1, 1).setPiece(Piece.O);
        grid.cell(1, 2).setPiece(Piece.X);

        grid.cell(2, 0).setPiece(Piece.X);
        grid.cell(2, 1).setPiece(Piece.O);
        grid.cell(2, 2).setPiece(Piece.X);

        return game;
    }

}
