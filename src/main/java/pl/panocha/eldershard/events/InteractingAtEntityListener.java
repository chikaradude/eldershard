package pl.panocha.eldershard.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InteractingAtEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        Entity entity = event.getRightClicked();
        if (entity.getType() != EntityType.VILLAGER) return;

        event.setCancelled(true);
        entity.remove();
    }
}
