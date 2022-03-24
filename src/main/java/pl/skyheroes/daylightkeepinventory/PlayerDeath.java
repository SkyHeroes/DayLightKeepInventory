package pl.skyheroes.daylightkeepinventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    private final int hourStartKeeping;
    private final int hourStopKeeping;

    public PlayerDeath(int hourStartKeeping, int hourStopKeeping) {
        this.hourStartKeeping = hourStartKeeping;
        this.hourStopKeeping = hourStopKeeping;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        var player = event.getEntity();
        var world = player.getWorld();
        int hour = (int) (world.getTime() / 1000);
        if (hour > hourStartKeeping && hour < hourStopKeeping) {
            event.setKeepInventory(true);
        }
    }
}
