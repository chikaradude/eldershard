package pl.panocha.eldershard;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.*;
import pl.panocha.eldershard.events.*;
import pl.panocha.eldershard.misc.ConfigManager;
import pl.panocha.eldershard.misc.VoiceIntegrationPlugin;
import pl.panocha.eldershard.systems.animations.AnimationEngine;

import java.util.Objects;

import static pl.panocha.eldershard.events.InteractListener.seats;

public final class Eldershard extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabling started.");

        Eldershard instance = this;

        saveDefaultConfig();

        ConfigManager.init(this);
        ConfigManager.getInstance().debug("Debug mode enabled.");

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
        Objects.requireNonNull(this.getCommand("ryszard"))
                .setExecutor(new RyszardCommand());

        BukkitVoicechatService service = getServer()
                .getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            service.registerPlugin(new VoiceIntegrationPlugin());
        }

        AnimationEngine.initialize(this);

        getLogger().info("Enabling finished.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling started.");

        for (AreaEffectCloud seat : seats.values()) {
            seat.remove();
        }

        seats.clear();

        getLogger().info("Disabling finished.");
    }
}
