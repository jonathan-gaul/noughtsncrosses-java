package tech.gaul.noughtsncrosses.events;

import tech.gaul.noughtsncrosses.Game;
import tech.gaul.noughtsncrosses.GridCell;
import tech.gaul.noughtsncrosses.Piece;

import java.util.EventObject;

public class TurnTakenEvent extends EventObject {

    private final GridCell cell;

    /**
     * Constructs a TurnTakenEvent.
     *
     * @param source the object on which the Event initially occurred.
     * @param cell The location the piece was placed.
     * @throws IllegalArgumentException if source is null
     */
    public TurnTakenEvent(Game source, GridCell cell) {
        super(source);
        this.cell = cell;
    }

    public Game game() {
        return (Game)source;
    }

    public GridCell cell() {
        return cell;
    }
}
