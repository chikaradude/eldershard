package pl.panocha.eldershard.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class ShopgoalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!sender.hasPermission("eldershard.dev")) return true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "phoenixc giveKey kluczhandlarza " + player.getName() + " 1");
            player.playSound(player.getLocation(),
                    Sound.ENTITY_PILLAGER_CELEBRATE, 1, 1);
        }

        return true;
    }
}