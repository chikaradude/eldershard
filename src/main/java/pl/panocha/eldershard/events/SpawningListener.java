package pl.panocha.eldershard.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.concurrent.ThreadLocalRandom;

public class SpawningListener implements Listener {

    private static final ThreadLocalRandom RNG = ThreadLocalRandom.current();

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getType() != EntityType.PHANTOM) return;
        if (RNG.nextInt(10) == 0) return;

        event.setCancelled(true);
    }
}
