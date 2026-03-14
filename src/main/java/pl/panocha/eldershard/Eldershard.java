package pl.panocha.eldershard;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.*;
import pl.panocha.eldershard.events.*;
import pl.panocha.eldershard.misc.ConfigManager;
import pl.panocha.eldershard.systems.animations.AnimationEngine;

import java.util.Objects;

import static pl.panocha.eldershard.events.InteractListener.seats;

public final class Eldershard extends JavaPlugin {

    @Override
    public void onEnable() {
        Eldershard instance = this;

        saveDefaultConfig();

        ConfigManager.init(this);
        if (ConfigManager.getInstance().isDebug()) {
            getLogger().info("Debug mode enabled.");
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new InteractingAtEntityListener(), this);
        pm.registerEvents(new JoiningListener(instance), this);
        pm.registerEvents(new WeatherChangeListener(), this);
        pm.registerEvents(new SpawningListener(), this);
        pm.registerEvents(new BreakingListener(), this);
        pm.registerEvents(new InteractListener(), this);
        pm.registerEvents(new QuitListener(), this);
        pm.registerEvents(new PortalListener(), this);

        Objects.requireNonNull(this.getCommand("test"))
                .setExecutor(new TestCommand());

        Objects.requireNonNull(this.getCommand("openchest"))
                .setExecutor(new OpenChestCommand(instance));

        Objects.requireNonNull(this.getCommand("discord"))
                .setExecutor(new DiscordCommand());

        Objects.requireNonNull(this.getCommand("sklep"))
                .setExecutor(new SklepCommand());

        Objects.requireNonNull(this.getCommand("start"))
                .setExecutor(new StartCommand(instance));

        Objects.requireNonNull(this.getCommand("glow"))
                .setExecutor(new GlowCommand());

        Objects.requireNonNull(this.getCommand("grzesiek"))
                .setExecutor(new GrzesiekCommand());

        //BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        //if (service != null) {
        //    service.registerPlugin(new VoiceIntegrationPlugin());
        //}
        // depend: [ voicechat ] (add to plugin.yml)

        AnimationEngine.initialize(this);

        getLogger().info("Eldershard enabling finished.");
    }

    @Override
    public void onDisable() {
        for (AreaEffectCloud seat : seats.values()) {
            seat.remove();
        }
        seats.clear();

        getLogger().info("Eldershard disabled.");
    }
}
