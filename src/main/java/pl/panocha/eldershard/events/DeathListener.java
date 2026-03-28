package pl.panocha.eldershard.events;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import pl.panocha.eldershard.misc.ConfigManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static pl.panocha.eldershard.misc.GeneralUtils.getWielkanocnaLzaItem;

public class DeathListener implements Listener {

    private final Set<UUID> rewardedPlayers = new HashSet<>();

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        UUID uuid = event.getEntity().getKiller().getUniqueId();

        if (rewardedPlayers.contains(uuid)) {
            ConfigManager.getInstance().debug("tried killing wielkanocna lza," +
                    " but rewardedPlayers already contains " + uuid);
            return;
        }

        Location location = event.getEntity().getLocation();

        World world = location.getWorld();
        if (world == null) return;

        location.getWorld().dropItem(location, getWielkanocnaLzaItem());

        rewardedPlayers.add(uuid);

        ConfigManager.getInstance().debug("added killing wielkanocna lza to " + uuid);
    }
}
