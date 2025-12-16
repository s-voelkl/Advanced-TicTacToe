/**
 * Represents the current state or condition of a Tic-Tac-Toe game.
 *
 * This enumeration defines the possible outcomes and states that can occur
 * during or at the end of a Tic-Tac-Toe game. It is used to determine whether
 * the game has concluded with a winner, ended in a draw, or is still ongoing.
 *
 * @see project.tictactoe.game
 */
package project.tictactoe.game;

public enum GameCondition {
    WIN,
    DRAW,
    NOT_FINISHED
}