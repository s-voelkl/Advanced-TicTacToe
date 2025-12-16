
package project.tictactoe;

import project.tictactoe.game.Game;

/**
 * Main class for the TicTacToe game application.
 *
 * This class serves as the entry point for running a TicTacToe game.
 * It initializes a game with configurable dimensions and win conditions,
 * allows players to be added interactively, and manages the game loop
 * where players alternate turns until the game concludes.
 *
 * @see project.tictactoe.game.Game
 */
public class TicTacToe {
    /**
     * Main entry point for the TicTacToe game application.
     * Initializes and runs a game loop where players take turns making moves.
     * The game board is displayed after each move with a brief delay between turns.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Generate a game based on user input.
        Game game = new Game();
        game.addPlayersInteractively();

        while (true) {
            game.print();
            game.move();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Waiting was not possible!");
            }
        }
    }
}