package project.tictactoe.game;

import java.util.ArrayList;
import java.util.List;

import project.tictactoe.board.IBoard;
import project.tictactoe.board.Board;
import project.tictactoe.board.Figure;
import project.tictactoe.io.CmdInput;
import project.tictactoe.io.CmdOutput;
import project.tictactoe.player.Player;
import project.tictactoe.player.PlayerFactory;

/**
 * Manages a Tic-Tac-Toe game session including board state, players, and game
 * flow.
 *
 * This class coordinates all aspects of a Tic-Tac-Toe game including player
 * management,
 * move execution, win condition checking, and game state transitions. It
 * supports multiple
 * players (up to the number of available symbols) and customizable board
 * dimensions and
 * win conditions.
 *
 * @see IBoard
 * @see Player
 * @see GameCondition
 */
public class Game {
    private IBoard board;
    private List<Player> players;
    private int nPlayers;
    private int minTilesToWin;

    /**
     * Constructs a new Game with specified parameters.
     *
     * Initializes the game with a board of given dimensions, number of players, and
     * win condition. Validates that parameters are within acceptable ranges.
     *
     * @param nPlayers      number of players (must be between 1 and available
     *                      symbols)
     * @param width         board width in tiles
     * @param height        board height in tiles
     * @param minTilesToWin minimum consecutive tiles needed to win
     * @throws IllegalArgumentException if n_players is out of valid range
     * @throws IllegalArgumentException if board dimensions are invalid
     * @throws IllegalArgumentException if min_tiles_to_win is invalid for the board
     *                                  size
     */
    public Game(int nPlayers, int width, int height, int minTilesToWin) {
        try {
            validateConstructorInputs(nPlayers, width, height, minTilesToWin);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        this.nPlayers = nPlayers;
        this.board = new Board(height, width);
        this.minTilesToWin = minTilesToWin;
        this.players = new ArrayList<>();
    }

    /**
     * Constructs a new Game using interactive input.
     *
     * Initializes the game by prompting the user for the number of players,
     * board dimensions, and win condition through CmdInput.
     *
     * @throws IllegalArgumentException if n_players is out of valid range
     * @throws IllegalArgumentException if board dimensions are invalid
     * @throws IllegalArgumentException if min_tiles_to_win is invalid for the board
     *                                  size
     */
    public Game() {
        int nPlayers = CmdInput.getPlayerCount();
        int width = CmdInput.getWidth();
        int height = CmdInput.getHeight();
        int minTilesToWin = CmdInput.getMinTilesToWin(width, height);

        try {
            validateConstructorInputs(nPlayers, width, height, minTilesToWin);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        this.nPlayers = nPlayers;
        this.board = new Board(height, width);
        this.minTilesToWin = minTilesToWin;
        this.players = new ArrayList<>();
    }

    /**
     * Validates the given Game constructor inputs and throws exceptions if needed.
     * 
     * @throws IllegalArgumentException if n_players is out of valid range
     * @throws IllegalArgumentException if board dimensions are invalid
     * @throws IllegalArgumentException if min_tiles_to_win is invalid for the board
     *                                  size
     */
    static void validateConstructorInputs(int nPlayers, int width, int height, int minTilesToWin) {
        if (nPlayers < 1 || nPlayers > Figure.commonSymbols.length) {
            throw new IllegalArgumentException("A game must have 1 to " + Figure.commonSymbols.length + " players!");
        }

        if (height < 1 || width < 1) {
            throw new IllegalArgumentException("Height and width must be at least 1!");
        }

        if (minTilesToWin < 1 || minTilesToWin > Math.max(width, height)) {
            throw new IllegalArgumentException("Invalid tiles to win given!");
        }
    }

    /**
     * Adds players to the game through interactive command-line prompts.
     *
     * Prompts the user to create each player one by one via the command line
     * interface.
     * The number of players to create is determined by the n_players field set
     * during
     * game construction.
     */
    public void addPlayersInteractively() {
        CmdOutput.createPlayersMessage(nPlayers);
        for (int i = 0; i < nPlayers; i++) {
            CmdOutput.createPlayerMessage(i + 1);
            this.players.add(PlayerFactory.createPlayer(board));
        }
    }

    /**
     * Adds players to the game non-interactively with specified human and bot
     * counts.
     *
     * Creates the specified number of bot and human players without user
     * interaction.
     * Bot players are added first, followed by human players.
     *
     * @param n_humans number of human players to create
     * @throws IllegalArgumentException if player counts don't sum to total
     *                                  n_players or are negative
     */
    public void addPlayersNonInteractively(int n_humans) {
        int n_bots = this.nPlayers - n_humans;
        if (n_bots < 0 || n_humans < 0 || n_humans + n_bots != this.nPlayers) {
            throw new IllegalArgumentException("The player counts are not correct.");
        }

        for (int i = 0; i < n_humans; i++) {
            this.players.add(PlayerFactory.createHumanPlayer(board));
        }

        for (int i = 0; i < n_bots; i++) {
            this.players.add(PlayerFactory.createBotPlayer(board));
        }
    }

    /**
     * Executes a single move in the game for the next player.
     *
     * Determines the next player based on turn count, executes their move, checks
     * for
     * win or draw conditions, and handles game completion and restart if needed.
     * The game continues if no end condition is met.
     *
     * @throws IllegalStateException if no players have been initialized
     * @throws IllegalStateException if all tiles are already occupied
     * @throws IllegalStateException if move execution fails
     */
    public void move() {
        // gets the first player with the least amount of turns done, acts its move.

        // condition: no players existing
        if (this.players.isEmpty()) {
            new IllegalStateException("No players available, they have to be initialized first!");
        }

        // condition: all tiles occupied.
        if (board.getEmptyTilePositions().isEmpty()) {
            throw new IllegalStateException("All tiles are already occupied.");
        }

        Player currentPlayer;
        try {
            currentPlayer = getNextPlayer();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        int position;
        try {
            position = currentPlayer.move();
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getLocalizedMessage());
        }

        GameCondition gc = this.checkWinCondition(position, currentPlayer);

        switch (gc) {
            case DRAW:
                this.finish(currentPlayer, position, gc);
                this.restart(true);
                break;

            case WIN:
                this.finish(currentPlayer, position, gc);
                this.restart(true);
                break;

            default:
                // NOT_FINISHED
                return;
        }

    }

    /**
     * Determines which player should take the next turn.
     *
     * Selects the player with the fewest completed turns. If multiple players have
     * the same turn count, the original player order is preserved (stable sort).
     *
     * @return the Player object whose turn is next
     * @throws IllegalStateException if no players are available
     */
    public Player getNextPlayer() {
        // Sort by fewest player turns first, then keep original order (stable sort)
        // source: from AuD :D
        // source for the stream: 5
        return players.stream()
                .sorted((p1, p2) -> Integer.compare(p1.getTurns(), p2.getTurns()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No players available."));
    }

    /**
     * Returns the game board.
     *
     * @return the IBoard instance used by this game
     */
    IBoard getBoard() {
        return this.board;
    }

    /**
     * Returns the list of players in the game.
     *
     * @return List of Player objects participating in the game
     */
    List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Returns the minimum number of consecutive tiles required to win.
     *
     * @return minimum tiles needed in a row to achieve victory
     */
    int getMinTilesToWin() {
        return this.minTilesToWin;
    }

    /**
     * Checks if a win or draw condition has been met after a player's move.
     *
     * Evaluates the board state after the specified position has been played by
     * the current player. Checks horizontal, vertical, and diagonal directions
     * for consecutive tiles matching the player's figure.
     *
     * @param position      the board position where the last move was made
     * @param currentPlayer the player who made the last move
     * @return GameCondition indicating WIN, DRAW, or NOT_FINISHED
     */
    GameCondition checkWinCondition(int position, Player currentPlayer) {
        // Sources 6, 7, 8, 9.
        // Convert position to x/y coordinates for easier directional checks
        int[] coords = board.getCoordsByPosition(position);
        int x = coords[0];
        int y = coords[1];

        Figure playerFigure = currentPlayer.getFigure();

        // Directions: horizontal (x), vertical (y), diagonal (2 directions, x & y)
        int[][] directions = {
                { 1, 0 },
                { 0, 1 },
                { 1, 1 },
                { 1, -1 }
        };

        // Check each direction
        for (int[] direction : directions) {
            // start at the current tile -> already 1 tile.
            int count = 1;

            // forward & backward, as the tile could be placed into the middle
            count += countConsecutive(x, y, direction[0], direction[1], playerFigure);
            count += countConsecutive(x, y, -direction[0], -direction[1], playerFigure);

            if (count >= minTilesToWin) {
                return GameCondition.WIN;
            }
        }

        // Condition: Draw if no free tiles and no win found.
        if (board.getEmptyTilePositions().isEmpty()) {
            return GameCondition.DRAW;
        }

        return GameCondition.NOT_FINISHED;
    }

    /**
     * Counts consecutive matching tiles in a specific direction from a starting
     * position.
     *
     * Iterates in the given direction (dx, dy) from the starting coordinates and
     * counts how many consecutive tiles match the specified figure.
     *
     * @param startX starting x-coordinate
     * @param startY starting y-coordinate
     * @param dx     direction increment for x-axis (-1, 0, or 1)
     * @param dy     direction increment for y-axis (-1, 0, or 1)
     * @param figure the Figure to match against
     * @return count of consecutive matching tiles in the specified direction
     */
    private int countConsecutive(int startX, int startY, int dx, int dy, Figure figure) {
        int count = 0;
        int x = startX + dx;
        int y = startY + dy;

        // while, checks done for ensuring to stay in boundaries
        while (x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight()) {
            Figure tile = board.getTile(board.getPositionByCoords(x, y));

            // Two player figures are the same if their figure is the same object.
            if (tile != null && tile.equals(figure)) {
                count++;
                x += dx;
                y += dy;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * Releases resources held by all players in the game.
     *
     * Calls dispose on each player to clean up any resources they may hold.
     */
    void dispose() {
        for (Player player : this.players) {
            player.dispose();
        }
    }

    /**
     * Completes the current game and displays the final state.
     *
     * Clears the screen, displays the final board state, player information,
     * and announces the game result based on the provided GameCondition.
     *
     * @param currentPlayer the player who made the last move
     * @param position      the position of the last move
     * @param gc            the GameCondition (WIN or DRAW) that ended the game
     */
    void finish(Player currentPlayer, int position, GameCondition gc) {
        CmdOutput.clearScreen();
        CmdOutput.printPlayers(players, currentPlayer);
        CmdOutput.printBoard(board);
        CmdOutput.finishGame(this.players, currentPlayer, this.board, position, gc);
    }

    /**
     * Resets the game state for a new round.
     *
     * Creates a new board with the same dimensions and resets all players.
     * Optionally prompts the user before restarting.
     *
     * @param interactively if true, prompts user before restarting; if false,
     *                      restarts immediately
     */
    void restart(boolean interactively) {
        if (interactively) {
            CmdInput.promptStopGame();
        }

        this.board = new Board(board.getHeight(), board.getWidth());

        for (Player player : players) {
            player.reset(board);
        }
    }

    /**
     * Displays the current game state to the console.
     *
     * Clears the screen and prints the current player list, active player
     * indicator,
     * and current board state.
     */
    public void print() {
        CmdOutput.clearScreen();
        CmdOutput.printPlayers(this.players, this.getNextPlayer());
        CmdOutput.printBoard(board);
    }

}
