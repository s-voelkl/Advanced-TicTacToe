package project.tictactoe.player;

import java.util.List;
import java.util.Random;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;

/**
 * Represents an automated player (bot) in a Tic-Tac-Toe game.
 * 
 * The bot makes moves automatically by randomly selecting from available empty
 * tiles.
 * This class extends the Player class and implements the actMove method to
 * provide
 * bot-specific behavior. The class maintains a count of all bot instances
 * created.
 * 
 * @see Player
 * @see IBoard
 */
public class Bot extends Player {

    private static int botCount = 0;

    /**
     * Creates a new Bot player with the specified figure, board, and name.
     * 
     * Increments the global bot count when a new bot is instantiated.
     * 
     * @param figure the game figure (X or O) that this bot will use
     * @param board  the game board on which this bot will play
     * @param name   the name of this bot player
     * @see Player#Player(Figure, IBoard, String)
     */
    public Bot(Figure figure, IBoard board, String name) {
        super(figure, board, name);
        botCount++;
    }

    /**
     * Returns the total number of bot instances currently in existence.
     * 
     * @return the current count of bot instances
     */
    public static int getBotCount() {
        return Bot.botCount;
    }

    /**
     * Executes the bot's move by randomly selecting an empty tile on the board.
     * 
     * The bot retrieves all available empty tile positions, randomly selects one,
     * converts it to coordinates, and places its figure on that tile.
     * 
     * @return the linear position index of the chosen tile
     * @throws IllegalStateException     if no empty tiles are available on the
     *                                   board
     * @throws IndexOutOfBoundsException if the chosen position cannot be converted
     *                                   to valid coordinates
     * @see IBoard#getEmptyTilePositions()
     * @see IBoard#getCoordsByPosition(int)
     * @see IBoard#setOnTile(Figure, int, int)
     */
    @Override
    protected int actMove() {
        List<Integer> emptyIndices = board.getEmptyTilePositions();

        // Can only place on empty tiles, but can normally not be called if no tiles
        // available.
        if (emptyIndices.isEmpty()) {
            throw new IllegalStateException("No empty tiles available.");
        }

        // Pick a random index, choosing one random not empty tile.
        Random random = new Random(); // without seed!
        int chosenIndex = emptyIndices.get(random.nextInt(emptyIndices.size()));

        // Convert linear index back to coordinates. Position -> [x, y]
        int[] coordinates_xy;
        try {
            coordinates_xy = board.getCoordsByPosition(chosenIndex);
        } catch (IndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException(e.getLocalizedMessage());
        }

        // Place the bot's figure
        board.setOnTile(this.getFigure(), coordinates_xy[0], coordinates_xy[1]);
        return chosenIndex;
    }

    /**
     * Cleans up resources and decrements the bot count when the bot is disposed.
     * 
     * This method should be called when the bot is no longer needed to properly
     * maintain the global bot count.
     * 
     * @see Player#dispose()
     */
    @Override
    public void dispose() {
        super.dispose();
        botCount--;
    }

}
