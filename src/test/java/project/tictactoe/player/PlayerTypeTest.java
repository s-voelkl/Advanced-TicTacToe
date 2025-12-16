package project.tictactoe.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PlayerTypeTest {
    // see sources 3,4

    @Test
    void testEnumValuesExist() {
        // Assert: length, types
        assertEquals(2, PlayerType.values().length);
        assertTrue(java.util.Arrays.asList(PlayerType.values()).contains(PlayerType.Bot));
        assertTrue(java.util.Arrays.asList(PlayerType.values()).contains(PlayerType.Human));
    }

    @Test
    void testEnumNames() {
        assertEquals("Bot", PlayerType.Bot.name());
        assertEquals("Human", PlayerType.Human.name());
    }

}