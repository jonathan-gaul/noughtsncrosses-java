package tech.gaul.noughtsncrosses;

import tech.gaul.noughtsncrosses.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.events.TurnTakenEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {

    void turnTaken(TurnTakenEvent e);
    void gameFinished(GameFinishedEvent e);

}
