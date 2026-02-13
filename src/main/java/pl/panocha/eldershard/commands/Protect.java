package pl.panocha.eldershard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class Protect implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) return true;

        player.performCommand("expand vert");
        player.performCommand("rg define " + player.getName());
        return true;
    }
}
