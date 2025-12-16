package project.tictactoe.io;

import java.util.List;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;
import project.tictactoe.game.GameCondition;
import project.tictactoe.player.Player;


/**
 * Utility class for command-line output operations in the Tic-Tac-Toe game.
 * 
 * Provides static methods for displaying the game board, player information,
 * game results, and various messages to the console. Uses ANSI color codes
 * to enhance visual presentation of the game elements.
 * 
 * This class cannot be instantiated as it only contains static utility methods.
 */
public abstract class CmdOutput {

    // ANSI color codes. source: 12.
    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_TITLE = "\u001B[35m";
    private static final String COLOR_GRID = "\u001B[37m";
    private static final String COLOR_DRAW = "\u001B[36m";
    private static final String COLOR_NUMBERS = "\u001B[36m";
    private static final String COLOR_PLAYERS = "\u001B[33m";
    private static final String COLOR_EMPTY = "\u001B[90m"; 

    /**
     * Displays the current state of the game board to the console.
     * 
     * Renders a formatted grid with column and row numbers, borders, and
     * player figures. Uses ANSI color codes for visual differentiation of
     * grid elements, numbers, and player symbols.
     * 
     * @param board the game board to be displayed
     */
    public static void printBoard(IBoard board) {
        int width = board.getWidth();
        int height = board.getHeight();

        // Column headers. The real coordinates are always x-1, but the user would be
        // irritated if we start at 0.
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            System.out.print(COLOR_NUMBERS + String.format(" %2d ", x + 1) + COLOR_RESET);
        }
        System.out.println();

        // Top border
        System.out.print("    ");
        for (int x = 0; x < width; x++) {
            System.out.print(COLOR_GRID + "----");
        }
        System.out.print("-" + COLOR_RESET);
        System.out.println();

        // Rows
        for (int y = 0; y < height; y++) {
            System.out.print(COLOR_NUMBERS + String.format("%3d ", y + 1) + COLOR_RESET);
            for (int x = 0; x < width; x++) {
                int pos = board.getPositionByCoords(x, y);
                Figure tile = board.getTile(pos);
                String symbol = (tile == null) ? " " : tile.getSymbol();
                String color = (tile == null) ? COLOR_EMPTY : COLOR_PLAYERS;

                System.out.print(COLOR_GRID + "|" + COLOR_RESET + color + String.format(" %s ", symbol) + COLOR_RESET);
            }
            System.out.println(COLOR_GRID + "|" + COLOR_RESET);

            // Separator
            System.out.print("    ");
            for (int x = 0; x < width; x++) {
                System.out.print(COLOR_GRID + "----");
            }
            System.out.print("-" + COLOR_RESET);

            System.out.println();
        }
    }

    /**
     * Displays an overview of all players and highlights the current player.
     * 
     * Shows each player's name, figure symbol, and number of turns taken.
     * The current player is marked with an arrow symbol and highlighted
     * using color codes.
     * 
     * @param players the list of all players in the game
     * @param currentPlayer the player whose turn it currently is
     */
    public static void printPlayers(List<Player> players, Player currentPlayer) {
        System.out.println();
        System.out.println(COLOR_PLAYERS + "Player Overview:" + COLOR_RESET);

        for (Player p : players) {
            boolean isCurrent = p.equals(currentPlayer);

            String marker = isCurrent ? COLOR_PLAYERS + "→ " + COLOR_RESET : "  "; // special symbol used!
            String nameColor = isCurrent ? COLOR_PLAYERS : COLOR_EMPTY;

            System.out.println(marker + nameColor + p.getName() + COLOR_RESET + " (" + p.getFigure().getSymbol()
                    + ")\twith " + p.getTurns() + " turn(s) yet.");
        }

        System.out.println();
    }

    /**
     * Clears the console screen.
     * 
     * Attempts to clear the screen using platform-specific commands for
     * Windows and Unix-like systems. Falls back to ANSI escape codes if
     * the platform-specific approach fails.
     */
    public static void clearScreen() {
        // try screen clear. source 13.
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // not catching exception, try other methods instead!
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    /**
     * Displays the game end message with results and final statistics.
     * 
     * Shows different messages depending on the game condition (win or draw).
     * For wins, displays the winner's information and the winning move coordinates.
     * For draws, displays a draw message.
     * 
     * @param players the list of all players in the game
     * @param currentPlayer the player who made the last move
     * @param board the final state of the game board
     * @param lastPosition the position index of the last move
     * @param gc the game condition indicating how the game ended (WIN or DRAW)
     */
    public static void finishGame(List<Player> players, Player currentPlayer, IBoard board, int lastPosition,
            GameCondition gc) {
        int[] coords = board.getCoordsByPosition(lastPosition);
        int xPos = coords[0];
        int yPos = coords[1];

        System.out.println("\n" + COLOR_TITLE + "=== GAME END ===" + COLOR_RESET);

        switch (gc) {
            case WIN:
                System.out.println(COLOR_PLAYERS + "Winner: " + currentPlayer.getName() + COLOR_RESET + " ("
                        + currentPlayer.getFigure().getSymbol()
                        + ") won with the last turn (x" + (xPos + 1) + "/y" + (yPos + 1) + ") with a total of "
                        + currentPlayer.getTurns()
                        + " turn(s).");
                break;

            case DRAW:
                System.out.println(COLOR_DRAW + "Draw! No turns are possible anymore." + COLOR_RESET);
                break;

            default:
                return;
        }
        System.out.println();
    }

    /**
     * Displays a message prompting to create the specified number of players.
     * 
     * Informs the user about player creation requirements and available
     * player types (Human and Bot).
     * 
     * @param n_players the total number of players to be created
     */
    public static void createPlayersMessage(int n_players) {
        System.out.println("\nPlease create the " + n_players + " players before playing.");
        System.out.println("You can choose Human players (with names and individual symbols) and Bot players.");
    }

    /**
     * Displays a message for creating an individual player.
     * 
     * Shows which player number is currently being created in the sequence.
     * 
     * @param nth_player the sequential number of the player being created
     */
    public static void createPlayerMessage(int nth_player) {
        System.out.println("\n" + nth_player + ". player creation:");
    }

}
