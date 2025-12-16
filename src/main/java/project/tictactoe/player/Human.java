package project.tictactoe.player;

import java.util.List;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;
import project.tictactoe.io.CmdInput;

/**
 * Represents a human player in a Tic-Tac-Toe game.
 * 
 * The human player makes moves through user input via the command line interface.
 * This class extends the Player class and implements the actMove method to provide
 * human-specific behavior where moves are determined by user input. The class maintains
 * a count of all human player instances created.
 * 
 * @see Player
 * @see IBoard
 * @see CmdInput
 */
public class Human extends Player {

    private static int humanCount = 0;

    /**
     * Creates a new Human player with the specified figure, board, and name.
     * 
     * Increments the global human player count when a new human is instantiated.
     * 
     * @param figure the game figure (X or O) that this human will use
     * @param board the game board on which this human will play
     * @param name the name of this human player
     * @see Player#Player(Figure, IBoard, String)
     */
    public Human(Figure figure, IBoard board, String name) {
        super(figure, board, name);
        humanCount++;
    }

    /**
     * Returns the total number of human player instances currently in existence.
     * 
     * @return the current count of human player instances
     */
    public static int getHumanCount() {
        return Human.humanCount;
    }

    /**
     * Executes the human's move by prompting for user input to select a tile.
     * 
     * The human player is prompted to enter coordinates for their move. The input
     * is validated to ensure it corresponds to an empty tile, then the figure is
     * placed at the chosen position.
     * 
     * @return the linear position index of the chosen tile
     * @throws IllegalStateException if no empty tiles are available on the board
     * @see IBoard#getEmptyTilePositions()
     * @see CmdInput#getTileCoordinates(IBoard, List)
     * @see IBoard#setOnTile(Figure, int, int)
     * @see IBoard#getPositionByCoords(int, int)
     */
    @Override
    protected int actMove() {
        List<Integer> emptyIndices = board.getEmptyTilePositions();

        // Can only place on empty tiles, but can normally not be called if no tiles
        // available.
        if (emptyIndices.isEmpty()) {
            throw new IllegalStateException("No empty tiles available.");
        }

        // check if in the allowed values, let the user input the value
        // int position = CmdInput.getIntValue("position on the board");
        int[] coordinates_xy = CmdInput.getTileCoordinates(board, emptyIndices);

        // Place the humans figure
        board.setOnTile(this.getFigure(), coordinates_xy[0], coordinates_xy[1]);
        return board.getPositionByCoords(coordinates_xy[0], coordinates_xy[1]);
    }

    /**
     * Cleans up resources and decrements the human player count when the player is disposed.
     * 
     * This method should be called when the human player is no longer needed to properly
     * maintain the global human player count.
     * 
     * @see Player#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        humanCount--;
    }
}
