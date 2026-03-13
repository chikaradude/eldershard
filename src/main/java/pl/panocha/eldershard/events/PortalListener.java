package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Objects;

public class PortalListener implements Listener {

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL) {
            event.setCancelled(true);

            World toWorld = (Objects.requireNonNull(
                    event.getFrom().getWorld()).getEnvironment() == World.Environment.NETHER)
                    ? Bukkit.getWorld("eldershard")
                    : Bukkit.getWorld("eldershard_nether");

            if (toWorld == null) return;

            Location target = (toWorld.getEnvironment() == World.Environment.NETHER)
                    ? new Location(toWorld, 50.5, 128, 50.5)
                    : new Location(toWorld, 0.5, 202, 0.5);

            event.getPlayer().teleport(target);
        }
    }
}
