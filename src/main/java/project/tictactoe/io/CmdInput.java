package project.tictactoe.io;

import java.util.List;
import java.util.Scanner;

import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;
import project.tictactoe.player.PlayerType;

/**
 * Provides command-line input utilities for the Tic-Tac-Toe game.
 *
 * This class handles all user input operations through the command line
 * interface,
 * including player configuration, move selection, and game flow control.
 * All methods are static and use a shared Scanner instance for input reading.
 *
 * @see project.tictactoe.player.PlayerType
 * @see project.tictactoe.board.Figure
 * @see project.tictactoe.board.IBoard
 */
public abstract class CmdInput {

    // Never close the scanner. Source 14.
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to select a player type.
     *
     * Displays options for Human (H) or Bot (B) players and validates the input.
     * Continues to prompt until a valid selection is made.
     *
     * @return the selected PlayerType (either Human or Bot)
     * @see project.tictactoe.player.PlayerType
     */
    public static PlayerType getPlayerType() {
        System.out.println("Select player type: (H) Human, (B) Bot");

        while (true) {
            System.out.print("Choice: ");
            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "H":
                    return PlayerType.Human;
                case "B":
                    return PlayerType.Bot;
                default:
                    System.err.println("Invalid choice! Please enter H for Human or B for Bot.");
            }
        }
    }

    /**
     * Prompts the user to enter a single character symbol for their game piece.
     *
     * Validates that the symbol is exactly one character long and not already in
     * use
     * by another player. Continues to prompt until a valid, unused symbol is
     * provided.
     *
     * @return a single character string representing the player's symbol
     * @see project.tictactoe.board.Figure#getUsedSymbols()
     */
    public static String getPlayerSymbol() {
        while (true) {
            System.out.println("Please choose your symbol (one character, e.g. X, O, #): ");
            String symbolInput = scanner.nextLine().strip();

            if (symbolInput.length() != 1) {
                System.err.println("Invalid input! Symbol must be exactly one character long.");
                continue;
            }

            if (Figure.getUsedSymbols().contains(symbolInput)) {
                System.err.println("This symbol is already taken! Please choose another one.");
                System.out.println("Already taken symbols: " + String.join(", ", Figure.getUsedSymbols()));
                continue;
            }

            return symbolInput;
        }
    }

    /**
     * Prompts the user to enter their player name.
     *
     * Validates that the name is at least one character long after trimming
     * whitespace.
     * Continues to prompt until a valid name is provided.
     *
     * @return the player's name as a non-empty string
     */
    public static String getPlayerName() {
        while (true) {
            System.out.print("Please enter your name: ");
            String name = scanner.nextLine().trim();

            if (name.length() > 0) {
                return name;
            } else {
                System.err.println("Invalid name! It must be at least one character long.");
            }
        }
    }

    /**
     * Prompts the user to decide whether to stop or restart the game.
     *
     * Accepts 'y' to terminate the program immediately or 'n' to return and
     * restart.
     * Continues to prompt until a valid input ('y' or 'n') is received.
     */
    public static void promptStopGame() {
        while (true) {
            System.out.print("Do you want to stop the game? [y/n]: ");
            String input;
            input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "y":
                    System.out.println("Game stopped!");
                    System.exit(0);
                    break;
                case "n":
                    System.out.println("Restarting the game!");
                    return;
                default:
                    System.err.println("Invalid input! Please enter 'y' for yes or 'n' for no.");
            }
        }
    }

    /**
     * Prompts the user to enter board coordinates for their next move.
     *
     * Coordinates are entered as 1-based indices (x for horizontal, y for
     * vertical).
     * Validates that coordinates are within board bounds and that the selected tile
     * is not already occupied. Continues to prompt until valid coordinates are
     * provided.
     *
     * @param board              the game board to validate coordinates against
     * @param emptyTilePositions list of available (empty) tile positions on the
     *                           board
     * @return an integer array containing the 0-based x and y coordinates [x, y]
     * @throws NumberFormatException if the user enters non-numeric values
     * @see project.tictactoe.board.IBoard
     */
    public static int[] getTileCoordinates(IBoard board, List<Integer> emptyTilePositions) {
        int width = board.getWidth();
        int height = board.getHeight();

        while (true) {
            try {
                System.out.println("Please enter the x (horizontal) and y (vertical) coordinates for your next move:");
                System.out
                        .println("x = horizontal (1 to " + (width) + "), y = vertical (1 to " + (height) + ")");

                System.out.print("Enter x: ");
                String xInput = scanner.nextLine().trim();
                System.out.print("Enter y: ");
                String yInput = scanner.nextLine().trim();

                // Convert to x-1 for user-friendlier input, starting at 1.
                int x = Integer.parseInt(xInput) - 1;
                int y = Integer.parseInt(yInput) - 1;

                if (x < 0 || x >= width || y < 0 || y >= height) {
                    System.err.println("Invalid coordinates! x must be between 0 and " + (width - 1) +
                            ", y must be between 0 and " + (height - 1) + ".");
                    continue;
                }

                int position = board.getPositionByCoords(x, y);
                if (!emptyTilePositions.contains(position)) {
                    System.err.println("This tile is already occupied by another figure! Please choose another one.");
                    continue;
                }

                return new int[] { x, y };

            } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter numeric values for x and y (e.g. 1, 2, 3).");
            }
        }
    }

    /**
     * Prompts the user to enter the number of players.
     *
     * Validates that the number is between 1 and the maximum number of available
     * symbols.
     * Continues to prompt until a valid number is provided.
     *
     * @return the number of players as a positive integer
     */
    public static int getPlayerCount() {
        int maxPlayers = Figure.commonSymbols.length;

        while (true) {
            try {
                System.out.print("Please enter the number of players (1 to " + maxPlayers + "): ");
                String input = scanner.nextLine().trim();
                int n_players = Integer.parseInt(input);

                if (n_players < 1 || n_players > maxPlayers) {
                    System.err.println("Invalid number! Please enter a number between 1 and " + maxPlayers + ".");
                    continue;
                }

                return n_players;

            } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter a numeric value.");
            }
        }
    }

    /**
     * Prompts the user to enter the board width.
     *
     * Validates that the width is a positive integer.
     * Continues to prompt until a valid width is provided.
     *
     * @return the board width as a positive integer
     */
    public static int getWidth() {
        while (true) {
            try {
                System.out.print("Please enter the board width: ");
                String input = scanner.nextLine().trim();
                int width = Integer.parseInt(input);

                if (width < 1) {
                    System.err.println("Invalid width! It must be at least 1.");
                    continue;
                }

                return width;

            } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter a numeric value.");
            }
        }
    }

    /**
     * Prompts the user to enter the board height.
     *
     * Validates that the height is a positive integer.
     * Continues to prompt until a valid height is provided.
     *
     * @return the board height as a positive integer
     */
    public static int getHeight() {
        while (true) {
            try {
                System.out.print("Please enter the board height: ");
                String input = scanner.nextLine().trim();
                int height = Integer.parseInt(input);

                if (height < 1) {
                    System.err.println("Invalid height! It must be at least 1.");
                    continue;
                }

                return height;

            } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter a numeric value.");
            }
        }
    }

    /**
     * Prompts the user to enter the minimum number of consecutive tiles needed to
     * win.
     *
     * Validates that the number is between 1 and the maximum of width and height.
     * Continues to prompt until a valid number is provided.
     *
     * @param width  the board width
     * @param height the board height
     * @return the minimum tiles to win as a positive integer
     */
    public static int getMinTilesToWin(int width, int height) {
        int maxTiles = Math.max(width, height);

        while (true) {
            try {
                System.out.print("Please enter the minimum consecutive tiles to win (1 to " + maxTiles + "): ");
                String input = scanner.nextLine().trim();
                int minTilesToWin = Integer.parseInt(input);

                if (minTilesToWin < 1 || minTilesToWin > maxTiles) {
                    System.err.println("Invalid number! Please enter a number between 1 and " + maxTiles + ".");
                    continue;
                }

                return minTilesToWin;

            } catch (NumberFormatException e) {
                System.err.println("Invalid input! Please enter a numeric value.");
            }
        }
    }
}
