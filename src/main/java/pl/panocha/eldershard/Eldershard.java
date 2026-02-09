package pl.panocha.eldershard;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.events.FishingListener;
import pl.panocha.eldershard.events.InteractingAtEntityListener;

public final class Eldershard extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new InteractingAtEntityListener(), this);

        getLogger().info("Eldershard enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Eldershard disabled.");
    }
}
