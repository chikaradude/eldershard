package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.panocha.eldershard.Eldershard;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class JoiningListener implements Listener {

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();
    private final ArrayList<UUID> joinedPlayers = new ArrayList<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (joinedPlayers.contains(uuid)) return;

        long delay = RNG.nextLong(60 * 20, 1800 * 20);
        Bukkit.getScheduler().runTaskLater(Eldershard.getInstance(), () -> {
            String playerName = event.getPlayer().getName();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "phoenixc giveKey kluczhandlarza " + playerName + " 1");
        }, delay);

        joinedPlayers.add(uuid);
    }
}
