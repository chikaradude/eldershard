package pl.panocha.eldershard.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.misc.FireworkUtils;

public class FwCommand implements CommandExecutor {

    private final FireworkUtils fireworkUtils = new FireworkUtils();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {

        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("eldershard.odkrywca")) return true;

        Location location = player.getLocation();
        assert location.getWorld() != null;

        fireworkUtils.renderRandomFirework(location);

        return true;
    }
}