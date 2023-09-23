package tech.gaul.noughtsncrosses.events;

import tech.gaul.noughtsncrosses.Game;

import java.util.EventObject;

public class GameFinishedEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param source the Grid  on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameFinishedEvent(Game source) {
        super(source);
    }

    public Game getGame() {
        return (Game)source;
    }
}
