package pl.panocha.eldershard.misc;

import net.md_5.bungee.api.ChatColor;

public class Utils {
    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
