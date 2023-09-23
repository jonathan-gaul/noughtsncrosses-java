package tech.gaul.noughtsncrosses;

import tech.gaul.noughtsncrosses.events.PiecePlacedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

    private final int size;

    private final GridCell[] grid;
    private int placedCount;
    private List<GridListener> listeners = new ArrayList<>();

    public Grid() {
        this(3);
    }

    public Grid(int size) {
        this.size = size;

        grid = new GridCell[size * size];
        for (var r = 0; r < size; r++) {
            for (var c = 0; c < size; c++) {
                GridCell cell = new GridCell(this, r, c);
                cell.addPropertyChangeListener(e -> piecePlaced((GridCell)e.getSource()));
                grid[c + size * r] = cell;
            }
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
     * Gets the size of the grid.
     * @return An integer value representing the size of the grid in cells.
     */
    public int size() {
        return size;
    }

    /**
     * Gets a grid reference representing a location on this grid.
     * @param row Row number the reference relates to.
     * @param column Column number the reference relates to.
     * @return A GridReference instance representing the requested location on this grid.
     */
    public GridCell cell(int row, int column) {
        return grid[column + size * row];
    }

    /**
     * Gets a list of all locations in the grid.
     * @return A List containing GridReferences representing every cell in the grid.
     */
    public List<GridCell> cells() {
        return Arrays.asList(grid);
    }

    /**
     * Gets a CellReference to a random cell in this grid.
     * @return A CellReference representing a random cell in this grid.
     */
    public GridCell getRandomEmptyCell() {
        var random = ThreadLocalRandom.current();

        for (var i = 0; i < 100; i++) {
            int row = random.nextInt(size);
            int col = random.nextInt(size);

            if (cell(row, col).isEmpty()) {
                return cell(row, col);
            }
        }

        return null;
    }

    /**
     * Fill the board with random pieces.
     */
    public void fillRandom() {
        int piece = 1;
        while (!isFull()) {
            getRandomEmptyCell().setPiece(piece == 1 ? Piece.O :Piece.X);
            piece = 3 - piece;
        }
    }

    /**
     * Reset this board, clearing all pieces.
     */
    public void reset() {
        placedCount = 0;
        for (var i = 0; i < size * size; i++) {
            grid[i].reset();
        }
    }

    // Event handling
    public void addGridListener(GridListener listener) {
        listeners.add(listener);
    }

    public void removeGridListener(GridListener listener) {
        listeners.remove(listener);
    }

    public void firePiecePlacedEvent(PiecePlacedEvent event) {
        for (var listener : listeners) {
            listener.piecePlaced(event);
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var i = 0; i < size * size; i++) {
            builder.append(' ');
            var piece = grid[i].getPiece();
            builder.append(piece == null ? " " : piece.toString());
            builder.append(' ');

            var lastCol = i % size == size - 1;
            var lastRow = i == size * size - 1;
            if (!lastCol) builder.append('|');

            if (lastCol) {
                builder.append(System.lineSeparator());
                if (!lastRow) {
                    builder.append("---+".repeat(size - 1)).append("---");
                    builder.append(System.lineSeparator());
                }
            }
        }

        return builder.toString();
    }

    private void piecePlaced(GridCell cell) {
        if (cell.getPiece() == Piece.O || cell.getPiece() == Piece.X) {
            placedCount++;
        }
    }
}
