package project.tictactoe.board;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game figure with a unique symbol for a Tic-Tac-Toe game.
 * 
 * This class manages game figures by ensuring that each figure has a unique single-character symbol.
 * It maintains a list of used symbols to prevent duplicates and provides utility methods to retrieve
 * available symbols from a predefined set of common symbols.
 * 
 * @see project.tictactoe.board
 */
public class Figure {
    private String symbol;

    public static final String[] commonSymbols = {
            "X", "O", "+", "#", "*", "@", "%", "&", "A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N",
            "P", "Q", "R", "S", "T", "U", "V", "W", "Y", "Z"
    };

    private static List<String> usedStrings = new ArrayList<>(); // has to be initialized as empty!

    /**
     * Creates a new Figure with the specified symbol.
     * 
     * This constructor calls the overloaded constructor with ignoreDuplicateSymbols set to false,
     * enforcing that the symbol must be unique among all existing figures.
     * 
     * @param symbol the single character symbol for this figure
     * @throws IllegalArgumentException if the symbol is not exactly one character without whitespace
     * @throws IllegalArgumentException if the symbol has already been used by another figure
     */
    public Figure(String symbol) {
        this(symbol, false);
    }

    /**
     * Creates a new Figure with the specified symbol and duplicate control option.
     * 
     * The symbol is stripped of leading and trailing whitespace before validation.
     * If ignoreDuplicateSymbols is false, the symbol must be unique among all existing figures.
     * Valid symbols are registered in the internal list of used symbols.
     * 
     * @param symbol the single character symbol for this figure
     * @param ignoreDuplicateSymbols if true, allows the same symbol to be used multiple times; if false, enforces uniqueness
     * @throws IllegalArgumentException if the symbol is not exactly one character without whitespace
     * @throws IllegalArgumentException if ignoreDuplicateSymbols is false and the symbol has already been used
     */
    public Figure(String symbol, boolean ignoreDuplicateSymbols) {
        symbol = symbol.strip();

        if (symbol.length() != 1) {
            throw new IllegalArgumentException("Symbol must be exactly one character without whitespace.");
        }

        if (ignoreDuplicateSymbols == false) {
            if (usedStrings.contains(symbol)) {
                throw new IllegalArgumentException("The same figure symbol cannot be picked twice!");
            }
            usedStrings.add(symbol);
        }

        this.symbol = symbol;
    }

    /**
     * Returns the symbol associated with this figure.
     * 
     * @return the single character symbol representing this figure
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Returns a list of all currently used symbols.
     * 
     * This static method provides access to all symbols that have been registered
     * by existing Figure instances.
     * 
     * @return a list containing all symbols currently in use
     */
    public static List<String> getUsedSymbols() {
        return usedStrings;
    }

    /**
     * Returns the first available symbol from the common symbols array.
     * 
     * This static method searches through the predefined commonSymbols array and returns
     * the first symbol that has not yet been used by any figure.
     * 
     * @return the first unused symbol from the common symbols array
     * @throws IllegalStateException if all common symbols have been exhausted
     */
    public static String getFreeSymbol() {
        for (String s : commonSymbols) {
            if (!usedStrings.contains(s)) {
                return s;
            }
        }
        throw new IllegalStateException("No more symbols available!");
    }

    /**
     * Releases this figure's symbol, making it available for reuse.
     * 
     * This method should be called explicitly when a figure is no longer needed,
     * as Java does not provide automatic destructor functionality. It removes the
     * figure's symbol from the list of used symbols, allowing it to be assigned
     * to new figures.
     */
    public void dispose() {
        // no Destructor available, finalize also not possible due to GC problems. So
        // use extra method for this!
        usedStrings.remove(this.symbol);
    }

}
