package project.tictactoe.board;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FigureTest {

    @Test
    public void constructorValidSymbolTest() {
        // Arrange
        String validSymbol = "X";

        // Act
        Figure figure = new Figure(validSymbol, true);

        // Assert
        assertNotNull(figure);
        figure.dispose();
    }

    @Test
    public void constructorInvalidSymbolTest() {
        // Arrange
        String invalidSymbol1 = "     ";
        String invalidSymbol2 = "AB";
        String invalidSymbol3 = " A B C ";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Figure(invalidSymbol1, true));
        assertThrows(IllegalArgumentException.class, () -> new Figure(invalidSymbol2, true));
        assertThrows(IllegalArgumentException.class, () -> new Figure(invalidSymbol3, true));
    }

    @Test
    public void constructorAlreadyUsedSymbolTest() {
        // Arrange
        String symbol = "X";

        // Act & Assert
        Figure figure = new Figure(symbol);
        assertThrows(IllegalArgumentException.class, () -> new Figure(symbol));
        assertThrows(IllegalArgumentException.class, () -> new Figure(symbol, false));

        figure.dispose();
    }

    @Test
    public void getSymbolTest() {
        // Arrange
        String symbol = "O";
        Figure figure = new Figure(symbol);

        // Act
        String result = figure.getSymbol();

        // Assert
        assertEquals(symbol, result);
        figure.dispose();
    }

    @Test
    public void getUsedSymbolsTest() {
        // Arrange
        String symbol1 = "X";
        String symbol2 = "Y";
        String symbol3 = "Z";
        String symbolNotInList = "~";
        List<String> usedSymbols = new ArrayList<>(Arrays.asList("X", "Y", "Z", "~"));

        // Act
        Figure figure1 = new Figure(symbol1);
        Figure figure2 = new Figure(symbol2);
        Figure figure3 = new Figure(symbol3);
        Figure figure4 = new Figure(symbolNotInList);

        // Assert
        assertEquals(usedSymbols, Figure.getUsedSymbols());

        figure1.dispose();
        figure2.dispose();
        figure3.dispose();
        figure4.dispose();
    }

    @Test
    public void disposeTest() {
        // Arrange
        String symbol1 = "X";
        List<String> usedSymbols = new ArrayList<>();
        Figure figure1 = new Figure(symbol1);

        // Act
        figure1.dispose();

        // Assert
        assertEquals(usedSymbols, Figure.getUsedSymbols());

    }

    @Test
    public void createSymbolAutomaticallyValidTest() {
        // Arrange
        String unexpected = Figure.commonSymbols[0];
        Figure figure1 = new Figure(unexpected);

        // Act
        String symbol = Figure.getFreeSymbol();
        Figure figure2 = new Figure(symbol);

        // Assert
        assertNotEquals(unexpected, symbol);
        assertNotEquals(figure1.getSymbol(), figure2.getSymbol());

        figure1.dispose();
        figure2.dispose();
    }

}