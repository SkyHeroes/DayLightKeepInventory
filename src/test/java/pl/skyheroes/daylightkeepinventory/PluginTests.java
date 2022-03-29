package pl.skyheroes.daylightkeepinventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.ChatColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PluginTests {

    private ServerMock server = null;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
    }

    @AfterEach
    void teardown() {
        MockBukkit.unmock();
        server = null;
    }

    @Test
    @DisplayName("The plugin correctly registers the event listeners")
    void pluginRegistersCorrectly() {
        final var plugin = MockBukkit.load(DayLightKeepInventory.class);
        final var config = plugin.getConfig();
        final var player = server.addPlayer();
        final var world = player.getWorld();
        world.setFullTime((config.getInt("hourStopKeepingInventory") + 18) * 1000L);
        player.setHealth(0d);
        assertEquals(ChatColor.stripColor(config.getString("lostInventoryMessage")),
            ChatColor.stripColor(player.nextMessage()));
    }
}
