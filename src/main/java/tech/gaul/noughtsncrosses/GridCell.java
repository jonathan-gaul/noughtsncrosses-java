package tech.gaul.noughtsncrosses;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Represents a cell on the game grid.
 */
public class GridCell {

    private final PropertyChangeSupport propertyChangeSupport;
    private final Grid grid;
    private final int row;
    private final int column;

    private Piece piece;

    GridCell(Grid grid, int row, int column) {

        propertyChangeSupport = new PropertyChangeSupport(this);

        if (row < 0 || column < 0 || row >= grid.size() || column >= grid.size()) {
            throw new IndexOutOfBoundsException();
        }

        this.grid = grid;
        this.row = row;
        this.column = column;
    }

    public Grid grid() {
        return grid;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Piece getPiece() {
        return piece;
    }

    /**
     * Attempt to place a piece in this cell.
     * @param piece The Piece to be placed.
     * @return true if the piece could be placed; otherwise, false.
     */
    public boolean setPiece(Piece piece) {
        if (this.piece != null) {
            return false;
        }

        this.piece = piece;
        propertyChangeSupport.firePropertyChange("Piece", null, piece);
        return true;
    }

    /**
     * Clear the piece in this cell without triggering any events.
     */
    public void reset() {
        this.piece = null;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
