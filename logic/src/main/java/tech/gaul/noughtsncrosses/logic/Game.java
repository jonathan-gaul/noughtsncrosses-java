package tech.gaul.noughtsncrosses.logic;

import tech.gaul.noughtsncrosses.logic.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.logic.events.TurnTakenEvent;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final Grid grid;
    private int turn = 1;
    private Piece winner;
    private Piece currentPiece;

    private final List<GameListener> listeners = new ArrayList<>();

    public Game() {
        this(3); // Default game size.
    }

    public Game(int size) {
        grid = new Grid(size);
        for (var cell : grid.cells()) {
            cell.addPropertyChangeListener(e -> piecePlaced((GridCell)e.getSource()));
        }
        currentPiece = Piece.X;
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
     * Gets a value that indicates the current player.
     * @return A Piece that indicates the current player.
     */
    public Piece getCurrentPiece() {
        return currentPiece;
    }

    /**
     * Sets a value that indicates the current player.
     * @param piece A Piece that indicates the current player.
     */
    public void setCurrentPiece(Piece piece) {
        if (piece == null) throw new IllegalArgumentException();
        currentPiece = piece;
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
        setCurrentPiece(firstPiece);

        int p = firstPiece == Piece.O ? 1 : 2;

        while (!isFinished()) {
            GridCell ref = putRandom();
            if (ref == null) break; // We couldn't place the piece for some reason.
        }

        return turn - 1;
    }

    private void piecePlaced(GridCell cell) {
        fireTurnTaken(new TurnTakenEvent(this, cell));
        updateWinCondition(cell);
        turn++;
        currentPiece = currentPiece == Piece.O ? Piece.X : Piece.O;
    }

    /**
     * Puts a piece on the board for the current player in a random (empty) location.
     * @return A GridReference representing the location the Piece was placed if successful; otherwise, null.
     */
    public GridCell putRandom() {
        // Try 100 times...
        for (int i = 0; i < 100; i++) {
            var cell = grid.getRandomEmptyCell();
            if (cell.setPiece(currentPiece))
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
                tD2 += grid.cell(i, 2 - i).getPiece() == piece ? 1 : 0;
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
        currentPiece = Piece.X;
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
