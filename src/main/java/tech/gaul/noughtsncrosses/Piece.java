package tech.gaul.noughtsncrosses;

public enum Piece {
    EMPTY,
    O,
    X;

    @Override
    public String toString() {
        return switch (this) {
            case EMPTY -> " ";
            case O -> "O";
            case X -> "X";
        };
    }
}
