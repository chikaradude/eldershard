package pl.panocha.eldershard.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.panocha.eldershard.misc.ConfigManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static pl.panocha.eldershard.misc.GeneralUtils.getWielkanocnaLzaItem;

public class BreakingListener implements Listener {

    private final Set<UUID> rewardedPlayers = new HashSet<>();

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Material material = event.getBlock().getType();

        if (material == Material.ANCIENT_DEBRIS) {
            event.setDropItems(false);
            return;
        }

        if (material.name().endsWith("_ORE")) {
            UUID uuid = event.getPlayer().getUniqueId();

            if (rewardedPlayers.contains(uuid)) {
                ConfigManager.getInstance().debug("tried mining wielkanocna lza," +
                        " but rewardedPlayers already contains " + uuid);
                return;
            }

            Block block = event.getBlock();
            block.getWorld().dropItem(block.getLocation(), getWielkanocnaLzaItem());

            rewardedPlayers.add(uuid);

            ConfigManager.getInstance().debug("added mining wielkanocna lza to " + uuid);
        }
    }
}
