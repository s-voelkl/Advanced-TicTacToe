

package project.tictactoe.board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game board for Tic-Tac-Toe with configurable dimensions.
 * 
 * The Board class manages a grid-based playing field where figures can be placed.
 * It provides coordinate and position conversion, tile management, and empty tile tracking.
 * The board uses a one-dimensional array internally to store tiles in row-major order.
 *
 * @see IBoard
 * @see Figure
 */
public class Board implements IBoard {
    private int height;
    private int width;

    private Figure[] tiles;

    /**
     * Constructs a new Board with the specified dimensions.
     * 
     * @param height the number of rows on the board
     * @param width the number of columns on the board
     * @throws IllegalArgumentException if height or width is less than 1
     */
    public Board(int height, int width) {
        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1!");
        }
        this.height = height;
        this.width = width;
        this.tiles = new Figure[width * height];
    }

    /**
     * Returns the width of the board.
     * 
     * @return the number of columns on the board
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the board.
     * 
     * @return the number of rows on the board
     */
    @Override
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns the maximum valid position index on the board.
     * 
     * @return the highest valid position index (width * height - 1)
     */
    int getMaxPosition() {
        return this.height * this.width - 1;
    }

    /**
     * Converts 2D coordinates to a linear position index.
     * 
     * @param xPos the x-coordinate (column index, 0-based)
     * @param yPos the y-coordinate (row index, 0-based)
     * @return the linear position index corresponding to the given coordinates
     * @throws IndexOutOfBoundsException if the coordinates are outside the board boundaries
     */
    @Override
    public int getPositionByCoords(int xPos, int yPos) {
        if (xPos < 0 || xPos >= this.width || yPos < 0 || yPos >= this.height) {
            throw new IndexOutOfBoundsException(
                    "Coordinates x" + xPos + "/y" + yPos + " are out of bounds:");
        }

        int linearIdx = xPos + (yPos * this.width);
        return linearIdx;
    }

    /**
     * Converts a linear position index to 2D coordinates.
     * 
     * @param position the linear position index
     * @return an array containing [xPos, yPos] coordinates
     * @throws IndexOutOfBoundsException if the position is outside the valid range
     */
    @Override
    public int[] getCoordsByPosition(int position) {
        if (position < 0 || position >= this.width * this.height) {
            throw new IndexOutOfBoundsException(
                    "Position " + position + " is out of bounds.");
        }

        int yPos = position / this.width;
        int xPos = position % this.width;

        return new int[] { xPos, yPos };
    }

    /**
     * Places a figure on the board at the specified coordinates.
     * 
     * @param figure the figure to place on the board
     * @param xPos the x-coordinate (column index, 0-based)
     * @param yPos the y-coordinate (row index, 0-based)
     * @return the figure that was placed
     * @throws IndexOutOfBoundsException if the coordinates are outside the board boundaries
     * @throws IllegalStateException if the tile at the specified position is already occupied
     */    
    @Override
    public Figure setOnTile(Figure figure, int xPos, int yPos) {
        int linearIdx = 0;
        try {
            linearIdx = getPositionByCoords(xPos, yPos);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getLocalizedMessage());
        }

        // default null value
        if (this.tiles[linearIdx] != null) {
            throw new IllegalStateException("Tile at position " + linearIdx + " is already occupied.");
        }

        this.tiles[linearIdx] = figure;
        return figure;
    }

    /**
     * Retrieves the figure at the specified position.
     * 
     * @param position the linear position index
     * @return the figure at the specified position, or null if the tile is empty
     * @throws IndexOutOfBoundsException if the position is outside the valid range
     */
    @Override
    public Figure getTile(int position) {
        if (position < 0 || position >= this.tiles.length) {
            throw new IndexOutOfBoundsException(
                    "Position " + position + " is out of bounds. Valid range: 0 to " + (this.tiles.length - 1));
        }

        return this.tiles[position];
    }

    /**
     * Returns the internal array of all tiles on the board.
     * 
     * @return the array of figures representing all tiles
     */
    Figure[] getTiles() {
        return this.tiles;
    }

    /**
     * Returns a list of all empty tile positions on the board.
     * 
     * @return a list of position indices where no figure is placed
     */
    public List<Integer> getEmptyTilePositions() {
        List<Integer> emptyPositions = new ArrayList<>();

        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                emptyPositions.add(i);
            }
        }
        return emptyPositions;
    }

}
