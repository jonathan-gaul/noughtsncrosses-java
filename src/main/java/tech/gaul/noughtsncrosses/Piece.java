package tech.gaul.noughtsncrosses;

public enum Piece {
    O,
    X;

    @Override
    public String toString() {
        return this == O ? "O" : "X";
    }
}
