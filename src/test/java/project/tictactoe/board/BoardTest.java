package project.tictactoe.board;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class BoardTest {
    @Test
    public void getMaxPosition() {
        // Arrange
        Board game3x3 = new Board(3, 3);
        Board game4x4 = new Board(4, 4);
        Board game5x5 = new Board(5, 5);

        // Act
        int result3x3 = game3x3.getMaxPosition();
        int result4x4 = game4x4.getMaxPosition();
        int result5x5 = game5x5.getMaxPosition();

        // Assert
        assertEquals(8, result3x3);
        assertEquals(15, result4x4);
        assertEquals(24, result5x5);
    }

    @Test
    public void getPositionByCoordsValid() {
        // Arrange
        Board board = new Board(5, 5);
        int x1 = 0;
        int y1 = 0;

        int x2 = 4;
        int y2 = 4;

        int x3 = 3;
        int y3 = 2;

        // Act
        int result1 = board.getPositionByCoords(x1, y1);
        int result2 = board.getPositionByCoords(x2, y2);
        int result3 = board.getPositionByCoords(x3, y3);

        // Assert
        assertEquals(0, result1);
        assertEquals(24, result2);
        assertEquals(13, result3);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "-1, -1",
            "0, -1",
            "5, 0",
            "0, 5",
            "5, 5",
    })
    public void getPositionByCoordsInvalidCoordinates(int x, int y) {
        // Arrange
        Board board = new Board(5, 5);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> board.getPositionByCoords(x, y));
    }

    @Test
    public void getCoordsByPositionValid() {
        // Arrange
        Board board = new Board(5, 5);
        int pos1 = 0; // (0,0)
        int pos2 = 24; // (4,4)
        int pos3 = 13; // (3,2)

        // Act
        int[] coords1 = board.getCoordsByPosition(pos1);
        int[] coords2 = board.getCoordsByPosition(pos2);
        int[] coords3 = board.getCoordsByPosition(pos3);

        // Assert
        assertArrayEquals(new int[] { 0, 0 }, coords1);
        assertArrayEquals(new int[] { 4, 4 }, coords2);
        assertArrayEquals(new int[] { 3, 2 }, coords3);
    }

    @ParameterizedTest
    @ValueSource(ints = { -1, 25, 30 })
    public void getCoordsByPositionInvalid(int position) {
        // Arrange
        Board board = new Board(5, 5);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCoordsByPosition(position));
    }

    @ParameterizedTest
    @CsvSource({ "0, 0", "0, 1", "3, 3", "4,4" })
    public void setOnTileValid(int x, int y) {
        // Arrange
        Board board = new Board(5, 5);
        Figure figure = new Figure("X", true);

        // Act
        board.setOnTile(figure, x, y);
        Figure result = board.getTiles()[board.getPositionByCoords(x, y)];

        // Assert
        assertEquals(figure.getSymbol(), result.getSymbol());
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 0",
            "-1, -1",
            "0, -1",
            "5, 0",
            "0, 5",
            "5, 5",
    })
    public void setOnTileInvalidIndex(int x, int y) {
        // Arrange
        Board board = new Board(5, 5);
        Figure figure = new Figure("X", true);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> board.setOnTile(figure, x, y));
    }

    @Test
    public void setOnTileInvalidState() {
        // Arrange
        Board board = new Board(5, 5);
        Figure figure = new Figure("X", true);

        board.setOnTile(figure, 0, 0);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> board.setOnTile(figure, 0, 0));
    }

    @Test
    void getTileDefaultValueIsNull() {
        // Arrange
        Board board = new Board(5, 5);

        // Act
        Figure tile = board.getTile(0);

        // Assert
        assertEquals(null, tile);
    }

    @Test
    public void getTileValid() {
        // Arrange
        Board board = new Board(5, 5);
        Figure figure = new Figure("X", true);

        board.setOnTile(figure, 0, 0);

        // Act
        Figure result = board.getTile(0);

        // Assert
        assertEquals(figure, result);
    }

    @ParameterizedTest
    @CsvSource({ "-4", "-2", "-1", "25", "26", "100", "1000" })
    public void getTileInvalid(int pos) {
        // Arrange
        Board board = new Board(5, 5);

        // Act & Assert
        assertThrows(IndexOutOfBoundsException.class, () -> board.getTile(pos));
    }

    @Test
    public void getEmptyTilePositionsTest() {
        // Arrange
        Board board = new Board(3, 3);

        board.setOnTile(new Figure("X", true), 0, 0);
        board.setOnTile(new Figure("O", true), 1, 1);
        List<Integer> correctPositions = Arrays.asList(1, 2, 3, 5, 6, 7, 8);

        // Act
        List<Integer> emptyPositions = board.getEmptyTilePositions();

        // Assert
        assertEquals(7, emptyPositions.size());
        assertEquals(correctPositions, emptyPositions);
    }

    @Test
    public void getEmptyTilePositionsEmptyTest() {
        // Arrange
        Board board = new Board(3, 3);
        List<Integer> correctPositions = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);

        // Act
        List<Integer> emptyPositions = board.getEmptyTilePositions();

        // Assert
        assertEquals(correctPositions.size(), emptyPositions.size());
        assertEquals(correctPositions, emptyPositions);
    }

    @Test
    public void getEmptyTilePositionsFullTest() {
        // Arrange
        Board board = new Board(3, 3);
        List<Integer> correctPositions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.setOnTile(new Figure("X", true), i, j);
            }
        }

        // Act
        List<Integer> emptyPositions = board.getEmptyTilePositions();

        // Assert
        assertEquals(correctPositions.size(), emptyPositions.size());
        assertEquals(correctPositions, emptyPositions);
    }

}
