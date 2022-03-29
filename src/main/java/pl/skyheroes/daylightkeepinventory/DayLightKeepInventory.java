package pl.skyheroes.daylightkeepinventory;

import com.google.common.annotations.VisibleForTesting;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public final class DayLightKeepInventory extends JavaPlugin {

    public DayLightKeepInventory() {
        super();
    }

    @VisibleForTesting
    private DayLightKeepInventory(JavaPluginLoader loader,
                                  PluginDescriptionFile description,
                                  File dataFolder,
                                  File file) {
        super(loader, description, dataFolder, file);
    }

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
