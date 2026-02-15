package pl.panocha.eldershard;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import de.maxhenkel.voicechat.api.events.Event;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.VoicechatServerStartedEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.OpenChestCommand;
import pl.panocha.eldershard.commands.ProtectCommand;
import pl.panocha.eldershard.events.FishingListener;
import pl.panocha.eldershard.events.InteractingAtEntityListener;
import pl.panocha.eldershard.events.JoiningListener;
import pl.panocha.eldershard.events.WeatherChangeListener;
import pl.panocha.eldershard.misc.VoiceIntegrationPlugin;

import java.util.Objects;

public final class Eldershard extends JavaPlugin {

    private static Eldershard instance;

    public static Eldershard getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new InteractingAtEntityListener(), this);
        pm.registerEvents(new JoiningListener(), this);
        pm.registerEvents(new WeatherChangeListener(), this);

        Objects.requireNonNull(this.getCommand("protect")).setExecutor(new ProtectCommand());
        Objects.requireNonNull(this.getCommand("openchest")).setExecutor(new OpenChestCommand());

        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            service.registerPlugin(new VoiceIntegrationPlugin());
        }

        getLogger().info("Eldershard enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Eldershard disabled.");
    }
}
