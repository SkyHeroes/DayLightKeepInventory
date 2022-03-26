package pl.skyheroes.daylightkeepinventory;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.LocalTime;

public class PlayerDeath implements Listener {

    private final TimeConverter timeConverter;
    private final LocalTime timeStartKeeping;
    private final LocalTime timeStopKeeping;
    private final String lostInventoryMessage;
    private final String keptInventoryMessage;

    public PlayerDeath(int hourStartKeeping, int hourStopKeeping,
                       String lostInventoryMessage,
                       String keptInventoryMessage) {
        this.timeConverter = new TimeConverter();
        this.timeStartKeeping = LocalTime.of(hourStartKeeping, 0);
        this.timeStopKeeping = LocalTime.of(hourStopKeeping, 0);
        this.lostInventoryMessage = ChatColor.translateAlternateColorCodes('&', lostInventoryMessage);
        this.keptInventoryMessage = ChatColor.translateAlternateColorCodes('&', keptInventoryMessage);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = event.getEntity();
        var world = player.getWorld();
        final var time = timeConverter.fromTicks(world.getTime());
        if (time.isAfter(timeStartKeeping) && time.isBefore(timeStopKeeping)) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            player.sendMessage(keptInventoryMessage);
        } else {
            player.sendMessage(lostInventoryMessage);
        }
    }
}
