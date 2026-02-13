package pl.panocha.eldershard;

import de.maxhenkel.voicechat.api.BukkitVoicechatService;
import de.maxhenkel.voicechat.api.ServerPlayer;
import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import pl.panocha.eldershard.commands.Protect;
import pl.panocha.eldershard.events.FishingListener;
import pl.panocha.eldershard.events.InteractingAtEntityListener;

import java.util.Objects;

public final class Eldershard extends JavaPlugin {

    private static Eldershard instance;
    private static VoicechatServerApi voicechatApi;

    public static boolean hasVoiceChat(Player player) {
        if (voicechatApi == null) return false;

        VoicechatConnection connection = voicechatApi.getConnectionOf((ServerPlayer) player);
        return connection != null && connection.isInstalled();
    }

    public static Eldershard getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FishingListener(), this);
        pm.registerEvents(new InteractingAtEntityListener(), this);

        Objects.requireNonNull(this.getCommand("protect")).setExecutor(new Protect());

        Bukkit.getServicesManager().register(
                BukkitVoicechatService.class,
                api -> voicechatApi = (VoicechatServerApi) api,
                this,
                ServicePriority.Normal
        );

        getLogger().info("Eldershard enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Eldershard disabled.");
    }
}
