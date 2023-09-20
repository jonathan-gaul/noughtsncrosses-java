package tech.gaul.noughtsncrosses;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

    private final int size;

    private final Piece[][] board;
    private int placedCount;

    private Piece winner;

    public Grid() {
        this(3);
    }

    public Grid(int size) {
        this.size = size;

        board = new Piece[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = Piece.EMPTY;
            }
        }
    }

    /**
     * Throw an IndexOutOfBoundsException if the given CellReference does not fall within the bounds of this grid.
     * @param ref The CellReference to be checked.
     */
    private void throwIfInvalidCellReference(CellReference ref) {
        if (ref.row() < 0 || ref.column() < 0 || ref.row() >= size || ref.column() >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Gets a value that indicates whether the board is full.
     * @return True if the board is full and no more pieces can be placed; otherwise, false.
     */
    public boolean isFull() {
        return placedCount >= size * size;
    }

    /**
     * Gets a value that indicates the winner of this game, if the game has finished.
     * @return The Piece representing the winner if the game has finished, Piece.EMPTY if the game was a draw, or
     *         null if the game has not yet finished.
     */
    public Piece getWinner() {
        return winner;
    }

    /**
     * Gets a value that indicates whether the game has finished.
     * @return true if the game has finished and no more pieces should be played; otherwise, false.
     */
    public boolean isFinished() {
        return winner != null;
    }

    /**
     * Gets the Piece at a given CellReference.
     * @param ref The cell reference to examine.
     * @return The Piece at the given cell reference.
     * @throws IndexOutOfBoundsException if the given cell reference does not fall within the bounds of this grid.
     */
    public Piece get(CellReference ref) {
        throwIfInvalidCellReference(ref);

        return board[ref.row()][ref.column()];
    }

    /**
     * Puts a piece at a cell reference.
     * @param ref Cell reference at which piece is to be placed.
     * @param piece The piece to place.
     * @return true if the piece was successfully placed, false otherwise.
     * @throws IndexOutOfBoundsException if the given cell reference does not fall within the bounds of this grid.
     */
    public boolean put(CellReference ref, Piece piece) {
        throwIfInvalidCellReference(ref);

        if (board[ref.row()][ref.column()] != Piece.EMPTY) {
            return false;
        }

        board[ref.row()][ref.column()] = piece;
        if (piece == Piece.O || piece == Piece.X)
            placedCount++;

        // Check for a win condition.
        int tRow = 0, tCol = 0, tD1 = 0, tD2 = 0;
        for (int i = 0; i < 3; i++) {
            var rowRef = new CellReference(i, ref.column());
            tRow += get(rowRef) == piece ? 1 : 0;

            var colRef = new CellReference(ref.row(), i);
            tCol += get(colRef) == piece ? 1 : 0;

            var d1Ref = new CellReference(i, i);
            tD1 += get(d1Ref) == piece ? 1 : 0;

            var d2Ref = new CellReference(size - 1 - i, i);
            tD2 += get(d2Ref) == piece ? 1 : 0;
        }

        if (tRow == size || tCol == size || tD1 == size || tD2 == size)
            winner = piece;
        else if (isFull())
            winner = Piece.EMPTY; // If the board is full, and we have no winner, it's a draw.

        return true;
    }

    /**
     * Get a CellReference to a random cell in this grid.
     * @return A CellReference representing a random cell in this grid.
     */
    public CellReference getRandomCellReference() {
        var random = ThreadLocalRandom.current();

        int row = random.nextInt(size);
        int col = random.nextInt(size);

        return new CellReference(row, col);
    }

    /**
     * Puts a piece on the board in a random (empty) location.
     * @param piece The piece to be placed.
     * @return true if the piece was successfully placed, false otherwise.
     */
    public boolean putRandom(Piece piece) {
        // Try 100 times...
        for (int i = 0; i < 100; i++) {
            var ref = getRandomCellReference();
            if (put(ref, piece))
                return true;
        }

        // Couldn't do it.
        return false;
    }

    /**
     * Fill the board with random pieces.
     */
    public void fillRandom() {
        int piece = 1;
        while (!isFull()) {
            putRandom(piece == 1 ? Piece.O :Piece.X);
            piece = 3 - piece;
        }
    }

    /**
     * Play the game until it has finished.
     * @param firstPiece The piece that goes first (O or X).
     * @param turnTaken A delegate to be called at the end of each turn.
     * @return The number of turns taken.
     */
    public int simulateGame(Piece firstPiece, TurnTaken turnTaken) {
        if (firstPiece == Piece.EMPTY)
            return 0;

        int p = firstPiece == Piece.O ? 1 : 2;

        int turn;
        for (turn = 1; turn <= size * size && !isFinished(); turn++) {
            Piece piece = p == 1 ? Piece.O : Piece.X;
            if (putRandom(piece)) {
                turnTaken.turnTaken(this, turn, piece);
            }
            p = 3 - p;
        }

        return turn - 1;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (int r = 0; r < size; r++) {

            for (int c = 0; c < size; c++) {
                var ref = new CellReference(r, c);
                builder.append(' ');
                builder.append(get(ref));
                builder.append(' ');
                if (c < size - 1)
                    builder.append('|');
            }
            builder.append(System.lineSeparator());

            if (r < size - 1) {
                for (int c = 0; c < size; c++) {
                    builder.append("---");
                    if (c < size - 1)
                        builder.append('+');
                }
                builder.append(System.lineSeparator());
            }
        }

        return builder.toString();
    }
}
