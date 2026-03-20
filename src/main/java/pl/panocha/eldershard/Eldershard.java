package pl.panocha.eldershard;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.*;
import pl.panocha.eldershard.events.*;
import pl.panocha.eldershard.misc.ConfigManager;
import pl.panocha.eldershard.misc.CrystalAnimation;
import pl.panocha.eldershard.misc.VoiceIntegrationPlugin;
import pl.panocha.eldershard.systems.animations.AnimationEngine;

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

        this.getCommand("test").setExecutor(new TestCommand());
        this.getCommand("openchest").setExecutor(new OpenChestCommand(instance));
        this.getCommand("discord").setExecutor(new DiscordCommand());
        this.getCommand("sklep").setExecutor(new SklepCommand());
        this.getCommand("start").setExecutor(new StartCommand(instance));
        this.getCommand("glow").setExecutor(new GlowCommand());
        this.getCommand("grzesiek").setExecutor(new GrzesiekCommand());
        this.getCommand("ryszard").setExecutor(new RyszardCommand());
        this.getCommand("fw").setExecutor(new FwCommand());
        this.getCommand("shopgoal").setExecutor(new ShopgoalCommand());

        BukkitVoicechatService service = getServer().getServicesManager().load(BukkitVoicechatService.class);
        if (service != null) {
            service.registerPlugin(new VoiceIntegrationPlugin());
        }

        AnimationEngine.initialize(this);

        new CrystalAnimation(this);

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
