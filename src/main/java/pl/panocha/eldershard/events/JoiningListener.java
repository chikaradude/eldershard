package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import pl.panocha.eldershard.misc.ConfigManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class JoiningListener implements Listener {

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    private static final long MIN_DELAY_TICKS = 60L * 20;     // 1 minute
    private static final long MAX_DELAY_TICKS = 1800L * 20;   // 30 minutes

    private static final String PERMISSION = "eldershard.obywatel";
    private static final String COMMAND_TEMPLATE =
            "phoenixc giveKey kluczhandlarza %s 1";

    private final Plugin plugin;
    private final Set<UUID> rewardedPlayers = new HashSet<>();

    public JoiningListener(Plugin plugin) {
        this.plugin = plugin;
    }

    private void scheduleReward(UUID uuid) {
        final long delay = RNG.nextLong(MIN_DELAY_TICKS, MAX_DELAY_TICKS);

        ConfigManager.getInstance().debug("Scheduling reward for player " + uuid + " in "
                + delay / 20 / 60 + " minutes");

        Bukkit.getScheduler().runTaskLater(plugin, () -> tryReward(uuid), delay);
    }

    private void tryReward(UUID uuid) {
        if (rewardedPlayers.contains(uuid)) {
            ConfigManager.getInstance().debug("tried, but rewardedPlayers already contains " + uuid);
            return;
        }

        final Player player = Bukkit.getPlayer(uuid);

        if (player == null || !player.isOnline()) {
            ConfigManager.getInstance().debug(uuid + " is null or not online");
            return;
        }

        if (!player.hasPermission(PERMISSION)) {
            ConfigManager.getInstance().debug(uuid + " has no permission to get reward");
            return;
        }

        giveReward(player);
        rewardedPlayers.add(uuid);
    }

    private void giveReward(Player player) {
        final String command = String.format(COMMAND_TEMPLATE, player.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

        ConfigManager.getInstance().debug("Reward distributed to " + player.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final UUID uuid = event.getPlayer().getUniqueId();

        if (rewardedPlayers.contains(uuid)) {
            ConfigManager.getInstance().debug("rewardedPlayers already contains " + uuid);
            return;
        }

        scheduleReward(uuid);
    }
}