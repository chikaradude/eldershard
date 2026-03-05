package pl.panocha.eldershard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jspecify.annotations.NonNull;

public class GlowCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("eldershard.odkrywca")) return true;

        PotionEffect effect = new PotionEffect(PotionEffectType.GLOWING, PotionEffect.INFINITE_DURATION, 0);
        player.addPotionEffect(effect);

        return true;
    }
}