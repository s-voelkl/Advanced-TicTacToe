package project.tictactoe.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import project.tictactoe.board.Board;
import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;

public class PlayerFactoryTest {
    @Test
    public void createBotPlayerTest() {
        // Arrange
        IBoard board = new Board(5, 5);
        String freeSymbolBefore = Figure.getFreeSymbol();

        // Act
        Player bot = PlayerFactory.createBotPlayer(board);

        // Assert
        assertEquals("Bot 1", bot.getName());
        assertEquals(freeSymbolBefore, bot.getFigure().getSymbol());

        bot.dispose();
    }

    @Test
    public void createHumanPlayerTest() {
        // Arrange
        IBoard board = new Board(5, 5);
        String freeSymbolBefore = Figure.getFreeSymbol();
        String playerName = "PlayerName";

        // Act
        Player human = PlayerFactory.createHumanPlayer(board, freeSymbolBefore, playerName);

        // Assert
        assertEquals(playerName, human.getName());
        assertEquals(freeSymbolBefore, human.getFigure().getSymbol());

        human.dispose();
    }

}