package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.panocha.eldershard.Eldershard;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.bukkit.Bukkit.getLogger;

public class JoiningListener implements Listener {

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();
    private final ArrayList<UUID> joinedPlayers = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (joinedPlayers.contains(uuid)) return;

        Player player = event.getPlayer();
        String playerName = player.getName();
        long delay = RNG.nextLong(60 * 20, 1800 * 20);

        getLogger().info("Scheduling reward for " + player.getName()
                + " to be given at " + (delay / 20 / 60) + " min.");

        Bukkit.getScheduler().runTaskLater(Eldershard.getInstance(), () -> {
            if (joinedPlayers.contains(uuid)) return;
            if (!player.hasPermission("eldershard.obywatel")) return;
            if (!player.isOnline()) return;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "phoenixc giveKey kluczhandlarza " + playerName + " 1");

            joinedPlayers.add(uuid);

            getLogger().info("Reward for " + player.getName() + " given successfully.");
        }, delay);
    }
}
