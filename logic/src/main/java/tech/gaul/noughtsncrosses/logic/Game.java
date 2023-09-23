package tech.gaul.noughtsncrosses.logic;

import tech.gaul.noughtsncrosses.logic.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.logic.events.TurnTakenEvent;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Grid grid;
    private int turn = 1;
    private Piece winner;

    private final List<GameListener> listeners = new ArrayList<>();

    public Game() {
        this(3); // Default game size.
    }

    public Game(int size) {
        grid = new Grid(size);
        for (var cell : grid.cells()) {
            cell.addPropertyChangeListener(e -> piecePlaced((GridCell)e.getSource()));
        }
    }

    /**
     * Gets the current turn number.
     * @return An integer value representing the current turn number.
     */
    public int turn() {
        return turn;
    }

    /**
     * Gets the winner of the game.
     * @return A Piece representing the winner, Piece.EMPTY if the game was a draw, or null if the game has not
     *         yet finished.
     */
    public Piece winner() {
        return winner;
    }

    /**
     * Gets a value that indicates whether the game has finished.
     * @return true if the game has finished and no more pieces should be played; otherwise, false.
     */
    public boolean isFinished() {
        return winner != null || grid.isFull();
    }

    /**
     * Play the game until it has finished.
     * @param firstPiece The piece that goes first (O or X).
     * @return The number of turns taken.
     */
    public int simulate(Piece firstPiece) {
        if (firstPiece == null)
            return 0;

        reset();

        int p = firstPiece == Piece.O ? 1 : 2;

        while (!isFinished()) {
            Piece piece = p == 1 ? Piece.O : Piece.X;

            GridCell ref = grid.getRandomEmptyCell();
            if (ref == null || !ref.setPiece(piece)) break; // We couldn't place the piece for some reason.

            updateWinCondition(ref);

            p = 3 - p;
        }

        return turn - 1;
    }

    private void piecePlaced(GridCell cell) {
        updateWinCondition(cell);
        fireTurnTaken(new TurnTakenEvent(this, cell));
        turn++;
    }

    /**
     * Puts a piece on the board in a random (empty) location.
     * @param piece The piece to be placed.
     * @return A GridReference representing the location the Piece was placed if successful; otherwise, null.
     */
    public GridCell putRandom(Piece piece) {
        // Try 100 times...
        for (int i = 0; i < 100; i++) {
            var cell = grid.getRandomEmptyCell();
            if (cell.setPiece(piece))
                return cell;
        }

        // Couldn't do it.
        return null;
    }

    /**
     * Check for a win condition based on a piece being placed at a specific location.
     * @param cell A GridReference representing the location the piece was placed.
     */
    private void updateWinCondition(GridCell cell) {

        // This works by checking the column and row the piece was placed on, and both diagonals.
        int tRow = 0, tCol = 0, tD1 = 0, tD2 = 0;

        final int size = grid.size();

        // We only need to check diagonals if we're on one.
        final boolean checkDiagonals = (cell.column() == cell.row()) || cell.column() == (size - 1 - cell.row());
        final Piece piece = cell.getPiece();

        for (int i = 0; i < size; i++) {
            // Check the row.
            tRow += grid.cell(cell.row(), i).getPiece() == piece ? 1 : 0;

            // Check the column.
            tCol += grid.cell(i, cell.column()).getPiece() == piece ? 1 : 0;

            if (checkDiagonals) {
                // Check the top-left to bottom-right diagonal.
                tD1 += grid.cell(i, i).getPiece() == piece ? 1 : 0;

                // Check the top-right to bottom-left diagonal.
                tD2 += grid.cell(i, i).getPiece() == piece ? 1 : 0;
            }
        }

        if (tRow == size || tCol == size || tD1 == size || tD2 == size) {
            winner = piece;
            fireGameFinished(new GameFinishedEvent(this));
        }
        else if (grid.isFull()) {
            winner = null; // If the board is full, and we have no winner, it's a draw.
            fireGameFinished(new GameFinishedEvent(this));
        }
    }

    /**
     * Reset this game, clearing all pieces and removing any win condition.
     */
    public void reset() {
        grid.reset();
        winner = null;
        turn = 1;
    }

    public Grid grid() {
        return grid;
    }

    // Event handlers
    public void addGameListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeGameListener(GameListener listener) {
        listeners.remove(listener);
    }

    public void fireTurnTaken(TurnTakenEvent e) {
        for (var listener : listeners) {
            listener.turnTaken(e);
        }
    }

    public void fireGameFinished(GameFinishedEvent e) {
        for (var listener : listeners) {
            listener.gameFinished(e);
        }
    }

    @Override
    public String toString() {

        return "TURN " + turn + ":" +
                System.lineSeparator() +
                grid.toString();
    }
}
