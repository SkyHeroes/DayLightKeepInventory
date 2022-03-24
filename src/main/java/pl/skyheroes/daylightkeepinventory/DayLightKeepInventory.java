package pl.skyheroes.daylightkeepinventory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class DayLightKeepInventory extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        int hourStartKeepingInventory = getConfig().getInt("hourStartKeepingInventory");
        int hourStopKeepingInventory = getConfig().getInt("hourStopKeepingInventory");
        Bukkit.getPluginManager().registerEvents(
                new PlayerDeath(hourStartKeepingInventory, hourStopKeepingInventory), this);
    }
}
