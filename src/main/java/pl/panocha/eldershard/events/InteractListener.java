package pl.panocha.eldershard.events;

import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class InteractListener implements Listener {

    public static final Map<UUID, AreaEffectCloud> seats = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        AreaEffectCloud oldSeat = seats.remove(uuid);
        if (oldSeat != null) {
            oldSeat.remove();
        }

        Location loc = event.getClickedBlock().getLocation().add(0.5, 0.2, 0.5);

        AreaEffectCloud seat = Objects.requireNonNull(
                loc.getWorld()).spawn(loc, AreaEffectCloud.class);

        seat.setRadius(0f);
        seat.setRadiusPerTick(0f);
        seat.setDuration(Integer.MAX_VALUE);
        seat.setWaitTime(0);
        seat.setReapplicationDelay(Integer.MAX_VALUE);
        seat.setInvulnerable(true);

        seat.addPassenger(player);

        seats.put(uuid, seat);
    }
}
