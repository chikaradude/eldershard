package pl.panocha.eldershard.events;

import de.maxhenkel.voicechat.api.VoicechatConnection;
import de.maxhenkel.voicechat.api.VoicechatServerApi;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.panocha.eldershard.Eldershard;
import pl.panocha.eldershard.misc.Utils;
import pl.panocha.eldershard.misc.VoiceIntegrationPlugin;

public class JoiningListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        /*
        Bukkit.getScheduler().runTaskLater(Eldershard.getInstance(), () -> {
            if (!player.isOnline()) return;

            VoicechatServerApi serverApi = VoiceIntegrationPlugin.getServerApi();
            VoicechatConnection voicechatConnection = serverApi.getConnectionOf(player.getUniqueId());
            boolean hasVoiceChat = voicechatConnection != null && voicechatConnection.isInstalled();

            if (hasVoiceChat) return;

            player.sendMessage(Utils.colorize("&fNasz serwer obsluguje m.in. &oSimpleVoiceChat&r &fczy &oEmotecraft&r&f. Sprawdz &a/discord&f, aby uzyskac szczegoly."));
        }, 20L * 95);
         */
    }
}
