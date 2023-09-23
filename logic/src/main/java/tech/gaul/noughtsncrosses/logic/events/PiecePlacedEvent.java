package tech.gaul.noughtsncrosses.logic.events;

import tech.gaul.noughtsncrosses.logic.GridCell;

import java.util.EventObject;

public class PiecePlacedEvent extends EventObject {

    /**
     * Constructs a new PiecePlacedEvent.
     *
     * @param source the GridCell on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null
     */
    public PiecePlacedEvent(GridCell source) {
        super(source);
    }

    public GridCell cell() {
        return (GridCell)source;
    }
}
