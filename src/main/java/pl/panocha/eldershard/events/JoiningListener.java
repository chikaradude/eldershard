package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.panocha.eldershard.Eldershard;

import static pl.panocha.eldershard.Eldershard.hasVoiceChat;

public class JoiningListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(Eldershard.getInstance(), () -> {
            if (!player.isOnline()) return;
            if (hasVoiceChat(player)) return;

            player.sendMessage(ChatColor.GREEN + "Rozmawiaj z nami na voice chacie, baw sie emotkami, glaszcz zwierzaki i wiele wiecej! Szczegoly na" + ChatColor.AQUA + "/discord");
        }, 20L * 75);
    }
}
