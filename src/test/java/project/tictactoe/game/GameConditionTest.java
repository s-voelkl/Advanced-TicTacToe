package project.tictactoe.game;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameConditionTest {

    @Test
    void testEnumValuesExist() {
        assertEquals(3, GameCondition.values().length);
        assertTrue(java.util.Arrays.asList(GameCondition.values()).contains(GameCondition.WIN));
        assertTrue(java.util.Arrays.asList(GameCondition.values()).contains(GameCondition.DRAW));
        assertTrue(java.util.Arrays.asList(GameCondition.values()).contains(GameCondition.NOT_FINISHED));
    }

    @Test
    void testEnumNames() {
        assertEquals("WIN", GameCondition.WIN.name());
        assertEquals("DRAW", GameCondition.DRAW.name());
        assertEquals("NOT_FINISHED", GameCondition.NOT_FINISHED.name());
    }
}
