package pl.panocha.eldershard.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class InteractingAtEntityListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        if (event.getRightClicked().getType() == EntityType.VILLAGER){
            event.setCancelled(true);
            event.getRightClicked().remove();
        }
    }
}
