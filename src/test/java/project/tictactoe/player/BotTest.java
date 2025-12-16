package project.tictactoe.player;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import project.tictactoe.board.Board;
import project.tictactoe.board.Figure;
import project.tictactoe.board.IBoard;

public class BotTest {
    @Test
    public void createBotPlayerTest() {
        // Arrange
        IBoard board = new Board(5, 5);
        String freeSymbolBefore = Figure.getFreeSymbol();

        // Act
        Player bot = PlayerFactory.createBotPlayer(board);

        // Assert
        assertEquals(1, Bot.getBotCount());
        assertEquals(freeSymbolBefore, bot.getFigure().getSymbol());
        assertEquals(0, bot.getTurns());

        // Act & Assert 2
        bot.dispose();
        assertEquals(new ArrayList<>(), Figure.getUsedSymbols());
        assertEquals(freeSymbolBefore, Figure.getFreeSymbol());
    }

    @Test
    public void moveTest() {
        // Arrange
        IBoard board = new Board(2, 2);
        Player bot = PlayerFactory.createBotPlayer(board);

        // only 1 option of 4 possible is available!
        board.setOnTile(bot.getFigure(), 0, 0);
        board.setOnTile(bot.getFigure(), 1, 0);
        board.setOnTile(bot.getFigure(), 0, 1);

        // Act
        int positionMoved = bot.move();

        // Assert
        assertNotEquals(positionMoved, 0);
        assertNotEquals(positionMoved, 1);
        assertNotEquals(positionMoved, 2);
        assertEquals(positionMoved, 3);
        assertEquals(1, bot.getTurns());

        assertEquals(new ArrayList<>(), board.getEmptyTilePositions());
        assertEquals(bot.getFigure(), board.getTile(positionMoved));

        bot.dispose();
    }

    @Test
    public void moveInvalidTest() {
        // Arrange
        IBoard board = new Board(2, 2);
        Player bot = PlayerFactory.createBotPlayer(board);

        // only 1 option of 4 possible is available!
        board.setOnTile(bot.getFigure(), 0, 0);
        board.setOnTile(bot.getFigure(), 1, 0);
        board.setOnTile(bot.getFigure(), 0, 1);
        board.setOnTile(bot.getFigure(), 1, 1);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> bot.move());

        bot.dispose();
    }

}