package tech.gaul.noughtsncrosses.logic;

public enum Piece {
    O,
    X;

    @Override
    public String toString() {
        return this == O ? "O" : "X";
    }
}
