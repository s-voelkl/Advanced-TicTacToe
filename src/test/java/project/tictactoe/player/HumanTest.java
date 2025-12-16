package project.tictactoe.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import project.tictactoe.board.Board;
import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;

public class HumanTest {
    @Test
    public void createHumanPlayerTest() {
        // Arrange
        IBoard board = new Board(5, 5);
        String freeSymbolBefore = Figure.getFreeSymbol();
        String playerName = "PlayerName";

        // Act
        Player human = PlayerFactory.createHumanPlayer(board, freeSymbolBefore, playerName);

        // Assert
        assertEquals(1, Human.getHumanCount());
        assertEquals(freeSymbolBefore, human.getFigure().getSymbol());
        assertEquals(0, human.getTurns());
        assertEquals(playerName, human.getName());

        // Act & Assert 2
        human.dispose();
        assertEquals(new ArrayList<>(), Figure.getUsedSymbols());
        assertEquals(freeSymbolBefore, Figure.getFreeSymbol());
    }

    @Test
    public void createHumanPlayerAutomaticallyTest() {
        // Arrange
        IBoard board = new Board(5, 5);
        String freeSymbolBefore = Figure.getFreeSymbol();

        // Act
        Player human = PlayerFactory.createHumanPlayer(board);

        // Assert
        assertEquals(1, Human.getHumanCount());
        assertEquals(freeSymbolBefore, human.getFigure().getSymbol());
        assertEquals(0, human.getTurns());
        assertEquals("Human 1", human.getName());

        // Act & Assert 2
        human.dispose();
        assertEquals(new ArrayList<>(), Figure.getUsedSymbols());
        assertEquals(freeSymbolBefore, Figure.getFreeSymbol());
    }

}