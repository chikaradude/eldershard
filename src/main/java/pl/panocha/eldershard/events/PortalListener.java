package pl.panocha.eldershard.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;

import java.util.Objects;

public class PortalListener implements Listener {

    private static final String OVERWORLD_NAME = "eldershard";
    private static final String NETHER_NAME = "eldershard_nether";

    private static final double OVERWORLD_X = 0.5;
    private static final double OVERWORLD_Y = 202;
    private static final double OVERWORLD_Z = 0.5;

    private static final double NETHER_X = 50.5;
    private static final double NETHER_Y = 128;
    private static final double NETHER_Z = 50.5;

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        if (event.getCause() != PlayerPortalEvent.TeleportCause.NETHER_PORTAL) return;

        event.setCancelled(true);

        World fromWorld = Objects.requireNonNull(event.getFrom().getWorld());
        World toWorld = (fromWorld.getEnvironment() == World.Environment.NETHER)
                ? Bukkit.getWorld(OVERWORLD_NAME)
                : Bukkit.getWorld(NETHER_NAME);

        if (toWorld == null) return;

        Location target = (toWorld.getEnvironment() == World.Environment.NETHER)
                ? new Location(toWorld, NETHER_X, NETHER_Y, NETHER_Z)
                : new Location(toWorld, OVERWORLD_X, OVERWORLD_Y, OVERWORLD_Z);

        event.getPlayer().teleport(target);
    }
}
