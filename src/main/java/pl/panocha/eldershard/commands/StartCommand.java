package pl.panocha.eldershard.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.Eldershard;
import pl.panocha.eldershard.misc.Utils;

public class StartCommand implements CommandExecutor {

    private final Plugin plugin;

    public StartCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;
        if (player.hasPermission("eldershard.player")) return true;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "lp user " + player.getName() + " parent set obywatel");

        player.sendMessage(Utils.colorize("&aRozpoczynanie..."));

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            if (!player.hasPermission("eldershard.player")) return;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "spawn " + player.getName());
        }, 20L);

        player.sendMessage(Utils.colorize("&aWszystko ustawione. Milej gry zyczy AI."));

        return true;
    }
}