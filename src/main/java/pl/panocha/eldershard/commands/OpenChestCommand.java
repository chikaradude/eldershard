package pl.panocha.eldershard.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.Eldershard;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getScheduler;

public class OpenChestCommand implements CommandExecutor {

    private void playVisuals(World world, Location location) {
        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 20, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 3) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 8);

        //faze2
        delay += 24;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 5, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 5, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 4), delay);

        //faze3
        delay += 24;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 3, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 9) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 2), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.spawnParticle(Particle.CLOUD, location, 1, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FLAME, location, 2, 0.1, 0.1, 0.1, 0.2);
                world.spawnParticle(Particle.FIREWORK, location, 2, 0.1, 0.1, 0.1, 0.2);

                counter.getAndIncrement();
                if (counter.get() >= 15) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 1), delay);

        //faze5
        delay += 15;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> world.spawnParticle(Particle.EXPLOSION, location, 1), delay);
    }

    private void playSounds(World world, Location location) {
        Sound sound = Sound.BLOCK_NOTE_BLOCK_BASS;
        float volume = 0.6f;

        //faze1
        int delay = 0;
        new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 4) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 5);

        //faze2
        delay += 20;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 5) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 4), delay);

        //faze3
        delay += 20;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 6) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 3), delay);

        //faze4
        delay += 18;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 7) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 2), delay);

        //faze5
        delay += 14;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> new BukkitRunnable() {
            final AtomicInteger counter = new AtomicInteger();

            @Override
            public void run() {
                assert world != null;
                world.playSound(location, sound, volume, volume);

                counter.getAndIncrement();
                if (counter.get() >= 8) {
                    cancel();
                }
            }
        }.runTaskTimer(Eldershard.getInstance(), 0, 1), delay);

        //faze6
        delay += 8;
        getScheduler().runTaskLater(Eldershard.getInstance(), () -> world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, volume, volume), delay);
    }

    private void updateItemEntitySettings(Player player, Item item) {
        item.setPickupDelay(30);
        item.setVisualFire(true);
        item.setCustomNameVisible(true);
        item.setOwner(player.getUniqueId());

        ItemMeta itemMeta = item.getItemStack().getItemMeta();
        assert itemMeta != null;
        String displayName = itemMeta.getDisplayName();
        item.setCustomName(displayName);
    }

    private void renderLateEffects(Location location, World world) {
        world.playSound(location, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        world.playSound(location, Sound.ITEM_BUNDLE_DROP_CONTENTS, 1f, 1f);
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command,
                             @NonNull String label, String @NonNull [] args) {
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage("player is null");
            return true;
        }

        Location location = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0, 2, 0);
        location.setY(player.getLocation().getY() + 1);

        World world = location.getWorld();

        playVisuals(world, location);
        playSounds(world, location);

        getScheduler().runTaskLaterAsynchronously(Eldershard.getInstance(), () -> {
            getScheduler().runTask(Eldershard.getInstance(), () -> {
                assert world != null;
                renderLateEffects(location, world);

                Item item = world.dropItem(location, new ItemStack(Material.TNT));
                updateItemEntitySettings(player, item);
            });
        }, 100);

        return true;
    }
}