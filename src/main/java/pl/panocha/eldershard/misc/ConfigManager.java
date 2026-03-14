package pl.panocha.eldershard.misc;

import org.bukkit.configuration.file.FileConfiguration;
import pl.panocha.eldershard.Eldershard;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getLogger;

public class ConfigManager {

    private static ConfigManager instance;

    private final Eldershard plugin;
    private FileConfiguration config;

    private ConfigManager(Eldershard plugin) {
        this.plugin = plugin;
        reload();
    }

    public static void init(Eldershard plugin) {
        instance = new ConfigManager(plugin);
    }

    public static ConfigManager getInstance() {
        return instance;
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public void debug(String message) {
        if (config.getBoolean("debug")) {
            getLogger().log(Level.INFO, message);
        }
    }
}
