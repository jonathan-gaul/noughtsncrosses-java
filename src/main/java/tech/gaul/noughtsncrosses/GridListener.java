package tech.gaul.noughtsncrosses;

import tech.gaul.noughtsncrosses.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.events.PiecePlacedEvent;

import java.util.EventListener;

/**
 * Delegate interface to be called when a piece is placed on a grid.
 */
public interface GridListener extends EventListener {
    /**
     * Signifies that a Piece was placed on the Grid.
     * @param e The event object representing the event.
     */
    void piecePlaced(PiecePlacedEvent e);

    /**
     * Signifies that the game was won.
     * @param e The event object representing the event.
     */
    void gameWon(GameFinishedEvent e);
}
