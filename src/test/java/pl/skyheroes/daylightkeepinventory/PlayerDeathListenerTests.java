package pl.skyheroes.daylightkeepinventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerDeathListenerTests {

    private ServerMock server = null;
    private JavaPlugin plugin = null;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.createMockPlugin();
    }

    @AfterEach
    void teardown() {
        MockBukkit.unmock();
        server = null;
        plugin = null;
    }

    @ParameterizedTest
    @DisplayName("Check if inventory is kept")
    @CsvSource(delimiter = '|', value = {
        "7  | 20 | 0     | false",
        "7  | 20 | 999   | false",
        "7  | 20 | 1000  | true",
        "7  | 20 | 1337  | true",
        "7  | 20 | 13999 | true",
        "7  | 20 | 14000 | false",
        "19 | 23 | 36000 | false",
    })
    void raiseEvent(int startHour, int endHour, long ticks, boolean expectedKeep) {
        final var listener = new PlayerDeath(startHour, endHour, "lost", "kept");
        final var pluginManager = server.getPluginManager();
        pluginManager.registerEvents(listener, plugin);

        final var player = server.addPlayer();
        player.getInventory().addItem(new ItemStack(Material.DIRT));
        final var world = player.getWorld();
        world.setTime(ticks);

        player.setHealth(0d);
        assertEquals(expectedKeep ? "kept" : "lost", player.nextMessage());
        pluginManager.assertEventFired(PlayerDeathEvent.class, event -> {
            assertEquals(expectedKeep, event.getKeepInventory());
            assertEquals(expectedKeep ? 0 : 1, event.getDrops().stream()
                .filter(Objects::nonNull)
                .count());
            return true;
        });
    }
}
