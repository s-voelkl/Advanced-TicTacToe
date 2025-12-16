package project.tictactoe.game;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import project.tictactoe.board.Figure;
import project.tictactoe.player.Bot;
import project.tictactoe.player.Human;
import project.tictactoe.player.Player;

public class GameTest {

    @Test
    public void constructorTest() {
        // Arrange
        int width = 4;
        int height = 4;
        int n_players = 2;
        int min_tiles_to_win = 3;

        // Act & Assert
        Game game = new Game(n_players, width, height, min_tiles_to_win);
        assertEquals(width, game.getBoard().getWidth());
        assertEquals(width, game.getBoard().getHeight());
        assertEquals(min_tiles_to_win, game.getMinTilesToWin());
        assertTrue(game.getPlayers().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "-5, 5, 3, 1",
            "5, -5, 3, 1",
            "0, 5, 3, 1",
            "5, 0, 3, 1",
            "5, 5, -5, 1",
            "5, 5, 0, 1",
            "0, 0, 0, 1",
            "-1, -1, -1, 1",
            "3, 3, 2, 0",
            "3, 3, 2, 4",
            "2, 5, 2, 6",
    })
    public void constructorInvalidTest(int width, int height, int n_players, int min_tiles_to_win) {

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> new Game(n_players, width, height, min_tiles_to_win));
    }

    @ParameterizedTest
    @CsvSource({
            "-5, 3",
            "3, 2",
    })
    public void addPlayersNonInteractivelyInvalidTest(int n_humans, int n_players) {
        Game game = new Game(n_players, 5, 5, 3);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> game.addPlayersNonInteractively(n_humans));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3, 0",
            "2, 3, 1",
            "0, 3, 3",
    })
    public void addPlayersNonInteractivelyValidTest(int n_humans, int n_players, int n_bots) {
        Game game = new Game(n_players, 5, 5, 3);
        int createdHumans = 0;
        int createdBots = 0;

        // Act
        game.addPlayersNonInteractively(n_humans);

        // Check for the player subclass counts
        for (Player player : game.getPlayers()) {
            if (player.getClass() == Human.class) {
                createdHumans++;
            } else if (player.getClass() == Bot.class) {
                createdBots++;
            }
        }

        // Assert
        assertEquals(n_players, game.getPlayers().size());
        assertEquals(n_humans, createdHumans);
        assertEquals(n_bots, createdBots);

        game.dispose();
    }

    @Test
    public void testHorizontalWin() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig = players[0].getFigure();

        game.getBoard().setOnTile(fig, 0, 0);
        game.getBoard().setOnTile(fig, 1, 0);
        int pos = game.getBoard().getPositionByCoords(2, 0);

        assertEquals(GameCondition.WIN, game.checkWinCondition(pos, players[0]));

        game.dispose();
    }

    @Test
    public void testVerticalWin() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig = players[0].getFigure();

        game.getBoard().setOnTile(fig, 0, 0);
        game.getBoard().setOnTile(fig, 0, 1);
        int pos = game.getBoard().getPositionByCoords(0, 2);

        assertEquals(GameCondition.WIN, game.checkWinCondition(pos, players[0]));

        game.dispose();
    }

    @Test
    public void testDiagonalWin() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig = players[0].getFigure();

        game.getBoard().setOnTile(fig, 0, 0);
        game.getBoard().setOnTile(fig, 1, 1);
        int pos = game.getBoard().getPositionByCoords(2, 2);

        assertEquals(GameCondition.WIN, game.checkWinCondition(pos, players[0]));

        game.dispose();
    }

    @Test
    public void testAntiDiagonalWin() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig = players[0].getFigure();

        game.getBoard().setOnTile(fig, 2, 0);
        game.getBoard().setOnTile(fig, 1, 1);
        int pos = game.getBoard().getPositionByCoords(0, 2);

        assertEquals(GameCondition.WIN, game.checkWinCondition(pos, players[0]));

        game.dispose();
    }

    @Test
    public void testDrawCondition() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig1 = players[0].getFigure();
        Figure fig2 = players[1].getFigure();

        // Fill board without any winning line

        game.getBoard().setOnTile(fig1, 0, 0);
        game.getBoard().setOnTile(fig2, 0, 1);
        game.getBoard().setOnTile(fig1, 0, 2);

        game.getBoard().setOnTile(fig2, 1, 0);
        game.getBoard().setOnTile(fig1, 1, 1);
        game.getBoard().setOnTile(fig2, 1, 2);

        game.getBoard().setOnTile(fig1, 2, 0);
        game.getBoard().setOnTile(fig2, 2, 1);
        game.getBoard().setOnTile(fig1, 2, 2);

        int pos = game.getBoard().getPositionByCoords(2, 2);
        assertEquals(GameCondition.DRAW, game.checkWinCondition(pos, players[1]));

        game.dispose();
    }

    @Test
    public void testNotFinishedCondition() {
        Game game = new Game(2, 3, 3, 3);
        game.addPlayersNonInteractively(1);
        Player[] players = game.getPlayers().toArray(new Player[0]);
        Figure fig = players[0].getFigure();

        game.getBoard().setOnTile(fig, 0, 0);
        int pos = game.getBoard().getPositionByCoords(0, 0);

        assertEquals(GameCondition.NOT_FINISHED, game.checkWinCondition(pos, players[0]));

        game.dispose();
    }

    @Test
    public void testNextTurnWithDifferentTurns() {
        // Arrange: Create game with 3 players
        Game game = new Game(3, 3, 3, 3);
        game.addPlayersNonInteractively(0); // only bots for testing
        Player[] players = game.getPlayers().toArray(new Player[0]);

        // Simulate: player[0] has 2 turns, player[1] has 1 turn, player[2] has 0
        // turns
        players[0].move();
        players[0].move();
        players[1].move();

        // Act
        Player next = game.getNextPlayer();

        // Assert:
        assertEquals(players[2], next);

        game.dispose();
    }

    @Test
    public void testNextTurnWithEqualTurnsKeepsOrder() {
        // Arrange: Create game with 3 players
        Game game = new Game(3, 3, 3, 3);
        game.addPlayersNonInteractively(0); // only bots for testing
        Player[] players = game.getPlayers().toArray(new Player[0]);

        // Simulate: All players have 1 turn
        players[0].move();
        players[1].move();
        players[2].move();

        // Act
        Player next = game.getNextPlayer();

        // Assert: Should return first player (stable sort)
        assertEquals(players[0], next);

        game.dispose();
    }

    @Test
    public void testNextTurnSinglePlayer() {
        // Arrange: Game with 1 player
        Game game = new Game(1, 3, 3, 3);
        game.addPlayersNonInteractively(0); // only bots for testing
        Player[] players = game.getPlayers().toArray(new Player[0]);

        // Act
        Player next = game.getNextPlayer();

        // Assert
        assertEquals(players[0], next);

        game.dispose();
    }

    @Test
    public void nextTurnInvalidTest() {
        // Arrange: o players added
        Game game = new Game(2, 3, 3, 3);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> game.getNextPlayer());

        game.dispose();
    }

    @Test
    public void resetGameTest() {
        // Arrange
        int width = 4;
        int height = 4;
        int n_players = 2;
        int min_tiles_to_win = 3;

        Game game = new Game(n_players, width, height, min_tiles_to_win);
        game.addPlayersNonInteractively(0); // only bots for testing

        int playerCountBefore = game.getPlayers().size();
        Player[] playersBefore = game.getPlayers().toArray(new Player[0]);
        playersBefore[0].move();

        // Act
        game.restart(false);
        Player[] playersAfter = game.getPlayers().toArray(new Player[0]);

        // Assert
        assertEquals(width, game.getBoard().getWidth());
        assertEquals(height, game.getBoard().getHeight());
        assertEquals(width * height, game.getBoard().getEmptyTilePositions().size());
        assertEquals(playerCountBefore, game.getPlayers().size());
        assertEquals(playersBefore[0].getFigure().getSymbol(), playersAfter[0].getFigure().getSymbol());

        game.dispose();
    }

}
