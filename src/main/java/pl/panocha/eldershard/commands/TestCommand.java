package pl.panocha.eldershard.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.systems.animations.AnimationBuilder;
import pl.panocha.eldershard.systems.animations.AnimationPriority;

import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public final class TestCommand implements CommandExecutor {

    public void playChestAnimation(Player player, Location loc) {
        World world = loc.getWorld();
        assert world != null;
        UUID owner = player.getUniqueId();

        AnimationBuilder.create()
                .owner(owner)
                .group("chest")
                .priority(AnimationPriority.CRITICAL)
                .duration(60)
                .onTick((tick, progress) -> {
                    world.spawnParticle(
                            Particle.CLOUD,
                            loc,
                            3
                    );
                    world.playSound(
                            loc,
                            Sound.BLOCK_CHEST_OPEN,
                            0.3f,
                            0.5f + (float) progress
                    );
                })
                .then(
                        AnimationBuilder.create()
                                .owner(owner)
                                .group("chest")
                                .priority(AnimationPriority.HIGH)
                                .duration(40)
                                .onTick((tick, progress) -> world.spawnParticle(
                                        Particle.FLAME,
                                        loc,
                                        2
                                ))
                )
                .then(
                        AnimationBuilder.create()
                                .owner(owner)
                                .group("chest")
                                .priority(AnimationPriority.CRITICAL)
                                .duration(10)
                                .onTick((tick, progress) -> {
                                    if (tick == 0) {
                                        world.spawnParticle(
                                                Particle.EXPLOSION,
                                                loc,
                                                1
                                        );
                                        world.playSound(
                                                loc,
                                                Sound.ENTITY_GENERIC_EXPLODE,
                                                1f,
                                                1f
                                        );
                                        Item item = world.dropItem(
                                                loc,
                                                new ItemStack(Material.NETHER_STAR)
                                        );
                                        item.setVelocity(player.getLocation().getDirection().multiply(0.5));
                                        item.setPickupDelay(3);
                                        item.setOwner(owner);
                                        item.setVisualFire(true);
                                        item.setCustomNameVisible(true);
                                    }
                                })
                )
                .start();
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {
        if (!(sender instanceof Player player)) return true;
        if (!player.hasPermission("eldershard.dev")) return true;

        Location location = player.getLocation()
                .add(player.getLocation().getDirection().normalize().multiply(3))
                .add(0, 2, 0);

        playChestAnimation(player, location);

        getLogger().warning("Player " + player.getName() + " checked test command");
        return true;
    }
}
