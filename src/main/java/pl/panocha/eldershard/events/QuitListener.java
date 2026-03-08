package pl.panocha.eldershard.events;

import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static pl.panocha.eldershard.events.InteractListener.seats;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        AreaEffectCloud seat = seats.remove(uuid);
        if (seat != null && !seat.isDead()) seat.remove();
    }
}
