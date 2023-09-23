package tech.gaul.noughtsncrosses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    @Test
    void givenFullGrid_whenIsFull_thenReturnTrue() {
        Grid grid = new Grid();

        for (var cell : grid.cells()) {
            cell.setPiece(Piece.X);
        }

        boolean expectedValue = true;
        boolean actualValue = grid.isFull();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenEmptyGrid_whenIsFull_thenReturnFalse() {
        Grid grid = new Grid();

        boolean expectedValue = false;
        boolean actualValue = grid.isFull();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenNearlyFullGrid_whenIsFull_thenReturnFalse() {
        Grid grid = new Grid();

        for (var cell : grid.cells().subList(0, grid.cells().size()-1)) {
            cell.setPiece(Piece.X);
        }

        boolean expectedValue = false;
        boolean actualValue = grid.isFull();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void getWinner() {
    }

    @Test
    void isFinished() {
    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void getRandomCellReference() {
    }

    @Test
    void putRandom() {
    }

    @Test
    void fillRandom() {
    }
}