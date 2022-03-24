package pl.skyheroes.daylightkeepinventory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DayLightKeepInventory extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        int hourStartKeepingInventory = getConfig().getInt("hourStartKeepingInventory");
        int hourStopKeepingInventory = getConfig().getInt("hourStopKeepingInventory");
        String savedInventoryMessage = getConfig().getString("savedInventoryMessage");
        String lostInventoryMessage = getConfig().getString("lostInventoryMessage");
        Bukkit.getPluginManager().registerEvents(
                new PlayerDeath(hourStartKeepingInventory, hourStopKeepingInventory,
                        lostInventoryMessage, savedInventoryMessage), this);
    }
}
