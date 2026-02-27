package pl.panocha.eldershard;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.*;
import pl.panocha.eldershard.events.FishingListener;
import pl.panocha.eldershard.events.InteractingAtEntityListener;
import pl.panocha.eldershard.events.JoiningListener;
import pl.panocha.eldershard.events.WeatherChangeListener;
import pl.panocha.eldershard.misc.VoiceIntegrationPlugin;
import pl.panocha.eldershard.systems.animations.AnimationEngine;

import java.util.Objects;

public final class Eldershard extends JavaPlugin {

    private static Eldershard instance;

    public static Eldershard getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        AnimationEngine.initialize(this);

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new InteractingAtEntityListener(), this);
        pm.registerEvents(new JoiningListener(), this);
        pm.registerEvents(new WeatherChangeListener(), this);

        Objects.requireNonNull(this.getCommand("test")).setExecutor(new TestCommand());
        Objects.requireNonNull(this.getCommand("protect")).setExecutor(new ProtectCommand());
        Objects.requireNonNull(this.getCommand("openchest")).setExecutor(new OpenChestCommand());
        Objects.requireNonNull(this.getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(this.getCommand("sklep")).setExecutor(new SklepCommand());

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
