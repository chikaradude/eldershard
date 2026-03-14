package pl.panocha.eldershard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.misc.ConfigManager;

public class RyszardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!sender.hasPermission("eldershard.dev")) return true;

        ConfigManager.getInstance().reload();

        return true;
    }
}