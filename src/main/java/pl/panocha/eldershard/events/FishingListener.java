package pl.panocha.eldershard.events;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class FishingListener implements Listener {

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) return;
        if (!(event.getCaught() instanceof Item item)) return;

        ItemStack stack = item.getItemStack();
        if (stack.getType() != Material.ENCHANTED_BOOK) return;

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getItemMeta();
        if (meta == null) return;

        if (meta.hasStoredEnchant(Enchantment.MENDING)) {
            meta.removeStoredEnchant(Enchantment.MENDING);
            meta.addStoredEnchant(Enchantment.UNBREAKING, 1, true);
            stack.setItemMeta(meta);
        }
    }
}
