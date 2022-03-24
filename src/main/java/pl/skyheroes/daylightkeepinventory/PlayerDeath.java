package pl.skyheroes.daylightkeepinventory;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private final int hourStartKeeping;
    private final int hourStopKeeping;
    private final String lostInventoryMessage;
    private final String keptInventoryMessage;

    public PlayerDeath(int hourStartKeeping, int hourStopKeeping,
                       String lostInventoryMessage,
                       String keptInventoryMessage) {
        this.hourStartKeeping = hourStartKeeping;
        this.hourStopKeeping = hourStopKeeping;
        this.lostInventoryMessage = ChatColor.translateAlternateColorCodes('&', lostInventoryMessage);
        this.keptInventoryMessage = ChatColor.translateAlternateColorCodes('&', keptInventoryMessage);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = event.getEntity();
        var world = player.getWorld();
        int hour = (int) (world.getTime() / 1000) + 6;
        if(hour > 24) hour -= 24;
        if (hour > hourStartKeeping && hour < hourStopKeeping) {
            event.setKeepInventory(true);
            event.getDrops().clear();
            player.sendMessage(keptInventoryMessage);
        } else {
            player.sendMessage(lostInventoryMessage);
        }
    }
}
