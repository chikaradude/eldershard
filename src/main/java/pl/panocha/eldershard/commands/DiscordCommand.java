package pl.panocha.eldershard.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("eldershard.help")) return true;

        TextComponent message = new TextComponent("§aKliknij tutaj, by otworzyć stronę");

        message.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL,
                "https://eldershard.pl"
        ));

        message.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("§aOtwiera stronę w przeglądarce").create()
        ));

        player.spigot().sendMessage(message);

        return true;
    }
}