package pl.panocha.eldershard.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jspecify.annotations.NonNull;
import pl.panocha.eldershard.Eldershard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class OpenChestCommand implements CommandExecutor {

    private static final int TOTAL_DURATION_TICKS = 110;

    private final Plugin plugin;

    public OpenChestCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String label,
            String[] args
    ) {

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /openchest <gracz>");
            return true;
        }

        Player player = Bukkit.getPlayerExact(args[0]);

        if (player == null || !player.isOnline()) {
            sender.sendMessage("§cGracz nie jest online.");
            return true;
        }

        if (!player.hasPermission("eldershard.openchest")) return true;

        Location effectLocation = calculateEffectLocation(player);

        new ChestAnimationTask(
                (Eldershard) plugin,
                player.getUniqueId(),
                effectLocation
        ).start();

        return true;
    }

    private Location calculateEffectLocation(Player player) {

        return player.getLocation()
                .add(player.getLocation().getDirection().normalize().multiply(3))
                .add(0, 2, 0);
    }

    /*
     * =========================
     * Animation Task
     * =========================
     */

    private static final class ChestAnimationTask extends BukkitRunnable {

        private final Eldershard plugin;
        private final UUID playerId;
        private final Location location;
        private final World world;

        private final List<TimedEvent> timeline;

        private int tick = 0;

        ChestAnimationTask(Eldershard plugin, UUID playerId, Location location) {

            this.plugin = plugin;
            this.playerId = playerId;
            this.location = location.clone();
            this.world = location.getWorld();

            this.timeline = buildTimeline();
        }

        void start() {
            runTaskTimer(plugin, 0L, 1L);
        }

        @Override
        public void run() {

            if (!isWorldValid()) {
                cancel();
                return;
            }

            executeTimelineTick(tick);

            tick++;

            if (tick > TOTAL_DURATION_TICKS) {
                cancel();
            }
        }

        private boolean isWorldValid() {
            return world != null;
        }

        private void executeTimelineTick(int currentTick) {

            for (TimedEvent event : timeline) {

                if (event.shouldRun(currentTick)) {
                    event.execute(world, location, playerId);
                }
            }
        }

        private List<TimedEvent> buildTimeline() {

            List<TimedEvent> events = new ArrayList<>();

            /*
             * VISUAL BUILDUP
             */

            events.add(TimedEvent.repeat(0, 8, 3,
                    (world, loc, player) ->
                            world.spawnParticle(Particle.CLOUD, loc, 20, .1, .1, .1, .2)));

            events.add(TimedEvent.repeat(24, 4, 6,
                    (world, loc, player) -> {
                        world.spawnParticle(Particle.CLOUD, loc, 5, .1, .1, .1, .2);
                        world.spawnParticle(Particle.FLAME, loc, 5, .1, .1, .1, .2);
                    }));

            events.add(TimedEvent.repeat(48, 2, 9,
                    (world, loc, player) -> {
                        world.spawnParticle(Particle.CLOUD, loc, 3, .1, .1, .1, .2);
                        world.spawnParticle(Particle.FLAME, loc, 2, .1, .1, .1, .2);
                        world.spawnParticle(Particle.FIREWORK, loc, 2, .1, .1, .1, .2);
                    }));

            events.add(TimedEvent.repeat(66, 1, 15,
                    (world, loc, player) -> {
                        world.spawnParticle(Particle.CLOUD, loc, 1, .1, .1, .1, .2);
                        world.spawnParticle(Particle.FLAME, loc, 2, .1, .1, .1, .2);
                        world.spawnParticle(Particle.FIREWORK, loc, 2, .1, .1, .1, .2);
                    }));

            /*
             * SOUND BUILDUP
             */

            events.add(TimedEvent.repeat(0, 5, 4,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, .6f, .6f)));

            events.add(TimedEvent.repeat(20, 4, 5,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, .6f, .6f)));

            events.add(TimedEvent.repeat(40, 3, 6,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, .6f, .6f)));

            events.add(TimedEvent.repeat(58, 2, 7,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, .6f, .6f)));

            events.add(TimedEvent.repeat(72, 1, 8,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.BLOCK_NOTE_BLOCK_BASS, .6f, .6f)));

            /*
             * FINAL EXPLOSION
             */

            events.add(TimedEvent.once(86,
                    (world, loc, player) ->
                            world.playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1f, 1f)));

            events.add(TimedEvent.once(100,
                    this::spawnReward));

            return events;
        }

        private void spawnReward(World world, Location loc, UUID playerId) {

            Player player = Bukkit.getPlayer(playerId);

            if (player == null) return;

            world.playSound(loc, Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
            world.playSound(loc, Sound.ITEM_BUNDLE_DROP_CONTENTS, 1f, 1f);

            Item item = world.dropItem(loc, new ItemStack(Material.TNT));

            configureRewardItem(item, playerId);
        }

        private void configureRewardItem(Item item, UUID playerId) {

            item.setPickupDelay(30);
            item.setOwner(playerId);
            item.setVisualFire(true);
            item.setCustomNameVisible(true);

            ItemMeta meta = item.getItemStack().getItemMeta();

            if (meta != null && meta.hasDisplayName()) {
                item.setCustomName(meta.getDisplayName());
            }
        }
    }

    /*
     * =========================
     * Timed Event System
     * =========================
     */

    private record TimedEvent(
            int startTick,
            int period,
            int repetitions,
            EventAction action
    ) {

        boolean shouldRun(int currentTick) {

            if (currentTick < startTick) return false;

            int delta = currentTick - startTick;

            if (period == 0) return delta == 0;

            return delta % period == 0 && delta / period < repetitions;
        }

        void execute(World world, Location location, UUID playerId) {
            action.execute(world, location, playerId);
        }

        static TimedEvent once(int tick, EventAction action) {
            return new TimedEvent(tick, 0, 1, action);
        }

        static TimedEvent repeat(int start, int period, int times, EventAction action) {
            return new TimedEvent(start, period, times, action);
        }
    }

    @FunctionalInterface
    private interface EventAction {

        void execute(World world, Location location, UUID playerId);
    }
}
