package tech.gaul.noughtsncrosses;

public class NoughtsAndCrosses {

    public static void main(String[] args) {

        Grid grid = new Grid();

        int turns = grid.simulateGame(Piece.O, (g, turn, piece) -> {
            System.out.println("TURN " + turn + ":");
            System.out.println(grid.toString());
            
            Piece winner = grid.getWinner();
            if (winner != null) {
                switch (grid.getWinner()) {
                    case O:
                        System.out.println("O wins!!");
                        break;
                    case X:
                        System.out.println("X wins!!");
                        break;
                    case EMPTY:
                        System.out.println("It's a draw!");
                        break;
                }
            }
        });

        System.out.println("Game finished after " + turns + " turn(s).");
    }

}
