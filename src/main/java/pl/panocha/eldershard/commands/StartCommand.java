package pl.panocha.eldershard.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.Eldershard;

import static pl.panocha.eldershard.Eldershard.getInstance;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("eldershard.player")) return true;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "lp user " + player.getName() + " parent set obywatel");

        player.sendMessage(ChatColor.GREEN + "Rozpoczynanie...");

        Bukkit.getScheduler().scheduleSyncDelayedTask(Eldershard.getInstance(), () -> {
            if (!player.hasPermission("eldershard.player")) return;

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    "spawn " + player.getName());
        }, 20L);

        return true;
    }
}