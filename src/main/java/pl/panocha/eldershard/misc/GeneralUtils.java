package pl.panocha.eldershard.misc;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GeneralUtils {
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    public static ItemStack getWielkanocnaLzaItem() {
        ItemStack item = new ItemStack(Material.GHAST_TEAR, 1);

        ItemMeta meta = item.getItemMeta();
        assert meta != null;

        meta.setDisplayName(colorize("&bWielkanocna Lza"));

        List<String> lore = new ArrayList<>();
        lore.add(colorize("&fGhast zaplakal, troche oh."));

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

}
