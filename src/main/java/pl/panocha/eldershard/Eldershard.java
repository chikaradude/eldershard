package pl.panocha.eldershard;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.events.FishingListener;

public final class Eldershard extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        getLogger().info("Eldershard enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Eldershard disabled.");
    }
}
