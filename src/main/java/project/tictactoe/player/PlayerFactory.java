package project.tictactoe.player;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;
import project.tictactoe.io.CmdInput;

/**
 * Factory class for creating Player instances in a Tic-Tac-Toe game.
 * 
 * This factory provides static methods to create different types of players (Human or Bot)
 * with appropriate configurations. It handles player type determination, symbol assignment,
 * and automatic naming conventions.
 */
public abstract class PlayerFactory {
    
    /**
     * Creates a Player instance based on user input from the command line.
     * 
     * Prompts the user to select a player type (Human or Bot) and collects necessary
     * information such as symbol and name for human players. Bot players are created
     * with auto-generated names and symbols.
     *
     * @param board the game board that the player will interact with
     * @return a fully configured Player instance (either Human or Bot)
     * @throws IllegalArgumentException if the player type is neither Human nor Bot
     * @see CmdInput#getPlayerType()
     * @see CmdInput#getPlayerSymbol()
     * @see CmdInput#getPlayerName()
     */
    public static Player createPlayer(IBoard board) {
        // Determine if Bot or Human
        PlayerType playerType = CmdInput.getPlayerType();

        switch (playerType) {
            case Human:
                String symbol = CmdInput.getPlayerSymbol();
                String name = CmdInput.getPlayerName();
                return createHumanPlayer(board, symbol, name);

            case Bot:
                return createBotPlayer(board);

            default:
                throw new IllegalArgumentException("Only Bot and Human players are possible!");
        }
    }

    /**
     * Creates a Human player with a specified symbol and name.
     * 
     * @param board the game board that the player will interact with
     * @param playerSymbol the symbol that represents this player on the board
     * @param playerName the display name for this player
     * @return a Human player instance with the specified configuration
     * @see Figure
     * @see Human
     */
    public static Player createHumanPlayer(IBoard board, String playerSymbol, String playerName) {
        Figure figure = new Figure(playerSymbol);
        return new Human(figure, board, playerName);
    }

    /**
     * Creates a Human player with default settings.
     * 
     * Automatically assigns a free symbol from the available pool and generates
     * a default name based on the current human player count.
     *
     * @param board the game board that the player will interact with
     * @return a Human player instance with auto-generated symbol and name
     * @see Figure#getFreeSymbol()
     * @see Human#getHumanCount()
     */
    public static Player createHumanPlayer(IBoard board) {
        Figure figure = new Figure(Figure.getFreeSymbol());
        String name = "Human " + (Human.getHumanCount() + 1);
        return new Human(figure, board, name);
    }

    /**
     * Creates a Bot player with default settings.
     * 
     * Automatically assigns a free symbol from the available pool and generates
     * a default name based on the current bot player count.
     *
     * @param board the game board that the player will interact with
     * @return a Bot player instance with auto-generated symbol and name
     * @see Figure#getFreeSymbol()
     * @see Bot#getBotCount()
     */
    public static Player createBotPlayer(IBoard board) {
        Figure figure = new Figure(Figure.getFreeSymbol());
        String name = "Bot " + (Bot.getBotCount() + 1);
        return new Bot(figure, board, name);
    }

}
