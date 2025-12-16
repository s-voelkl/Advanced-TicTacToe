package project.tictactoe.board;

import java.util.List;

/**
 * Defines the contract for a Tic-Tac-Toe game board.
 * 
 * This interface provides methods for managing the game board, including setting and
 * retrieving figures, converting between position indices and coordinates, and querying
 * available empty tiles. Implementations of this interface handle the board's state and
 * coordinate system.
 * 
 * @see Figure
 */
public interface IBoard {

    /**
     * Returns the width of the board.
     * 
     * @return the number of columns in the board
     */
    int getWidth();

    /**
     * Returns the height of the board.
     * 
     * @return the number of rows in the board
     */
    int getHeight();

    /**
     * Converts 2D board coordinates to a linear position index.
     * 
     * @param xPos the x-coordinate (column) on the board
     * @param yPos the y-coordinate (row) on the board
     * @return the linear position index corresponding to the given coordinates
     */
    int getPositionByCoords(int xPos, int yPos);

    /**
     * Converts a linear position index to 2D board coordinates.
     * 
     * @param position the linear position index on the board
     * @return an array containing the x and y coordinates [x, y]
     * @throws IndexOutOfBoundsException if the position is outside the valid range
     */
    int[] getCoordsByPosition(int position);

    /**
     * Places a figure at the specified coordinates on the board.
     * 
     * @param figure the figure (X or O) to place on the board
     * @param xPos the x-coordinate (column) where the figure should be placed
     * @param yPos the y-coordinate (row) where the figure should be placed
     * @return the figure that was previously at that position, or null if empty
     */
    Figure setOnTile(Figure figure, int xPos, int yPos);

    /**
     * Retrieves the figure at the specified position on the board.
     * 
     * @param position the linear position index on the board
     * @return the figure at the given position, or null if the tile is empty
     */
    Figure getTile(int position);

    /**
     * Returns a list of all empty tile positions on the board.
     * 
     * @return a list of linear position indices for all unoccupied tiles
     */
    List<Integer> getEmptyTilePositions();
}
