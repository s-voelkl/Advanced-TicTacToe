package project.tictactoe.player;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;

/**
 * Abstract base class representing a player in a Tic-Tac-Toe game.
 * 
 * This class manages the player's figure, name, turn count, and provides
 * the framework for making moves on the game board. Concrete player
 * implementations must extend this class and implement the actMove() method
 * to define their specific move strategy.
 */

public abstract class Player {
    private Figure figure;
    private String name;
    private int turns;
    protected IBoard board;

    /**
     * Constructs a new Player with the specified figure, board, and name.
     * 
     * This constructor initializes a player with their game piece (figure),
     * associates them with a game board, assigns them a name, and sets their
     * initial turn count to zero.
     *
     * @param figure the Figure object representing this player's game piece
     * @param board the IBoard instance where the player will make moves
     * @param name the name identifier for this player
     */
    protected Player(Figure figure, IBoard board, String name) {
        this.figure = figure;
        this.board = board;
        this.name = name;
        this.turns = 0;
    }
    
    /**
     * Returns the figure (symbol) associated with this player.
     *
     * @return the Figure object representing this player's game piece
     */
    public Figure getFigure() {
        return this.figure;
    }

    /**
     * Executes a move on the game board and increments the turn counter.
     * 
     * This method wraps the abstract actMove() method, ensuring that the
     * turn count is properly incremented after each successful move.
     *
     * @return the board position where the move was made
     * @throws IllegalStateException if the move operation fails or is invalid
     */
    public int move() {
        try {
            // Wrapper, so the turns are increased every step
            int position = this.actMove();
            this.turns++;
            return position;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Moving was not successful. " + e.getMessage());
        }
    }

    /**
     * Performs the actual move logic for this player.
     * 
     * This method must be implemented by concrete subclasses to define
     * their specific strategy for selecting and making a move on the board.
     *
     * @return the board position where the move was made
     */
    protected abstract int actMove();

    /**
     * Returns the total number of turns this player has taken.
     *
     * @return the count of moves made by this player
     */
    public int getTurns() {
        return this.turns;
    }

    /**
     * Resets the player's state for a new game.
     * 
     * This method clears the turn counter and updates the board reference,
     * preparing the player for a fresh game session.
     *
     * @param board the new game board to be used
     */
    public void reset(IBoard board) {
        this.turns = 0;
        this.board = board;
    }

    /**
     * Returns the name of this player.
     *
     * @return the player's name as a String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Releases resources held by this player, specifically disposing of the figure.
     * 
     * This method should be called when the player object is no longer needed
     * to ensure proper cleanup of associated resources.
     */
    public void dispose() {
        this.figure.dispose();
    }
}
