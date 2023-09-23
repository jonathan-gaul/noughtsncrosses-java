package tech.gaul.noughtsncrosses.logic;

import tech.gaul.noughtsncrosses.logic.events.GameFinishedEvent;
import tech.gaul.noughtsncrosses.logic.events.TurnTakenEvent;

public class NoughtsAndCrosses {

    public static void main(String[] args) {

        Game game = new Game();

        game.addGameListener(new GameListener() {
            @Override
            public void turnTaken(TurnTakenEvent e) {
                System.out.println(game.toString());
            }

            @Override
            public void gameFinished(GameFinishedEvent e) {
                Piece winner = game.winner();
                if (winner == null) {
                    System.out.println("It's a draw!");
                } else if (winner == Piece.O) {
                    System.out.println("O wins!!");
                } else if (winner == Piece.X) {
                    System.out.println("X wins!!");
                }
            }
        });

        int turns = game.simulate(Piece.O);

        if (game.isFinished()) {
            System.out.println("Game finished after " + turns + " turn(s).");
        }
        else {
            System.out.println("Simulation ended after " + turns + " turn(s).");
        }
    }

}
