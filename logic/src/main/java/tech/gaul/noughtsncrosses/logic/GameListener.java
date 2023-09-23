package tech.gaul.noughtsncrosses.logic;

import tech.gaul.noughtsncrosses.logic.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.logic.events.TurnTakenEvent;

import java.util.EventListener;

public interface GameListener extends EventListener {

    void turnTaken(TurnTakenEvent e);
    void gameFinished(GameFinishedEvent e);

}
