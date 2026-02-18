package pl.panocha.eldershard.systems.animations;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class AnimationEngine {

    private static AnimationEngine instance;

    public static AnimationEngine get() {
        return instance;
    }

    public static void initialize(Plugin plugin) {

        if (instance != null)
            throw new IllegalStateException();

        instance = new AnimationEngine(plugin);
    }

    private final Set<Animation> active = ConcurrentHashMap.newKeySet();

    private final Map<UUID, Set<Animation>> owners = new ConcurrentHashMap<>();

    private final Map<String, Set<Animation>> groups = new ConcurrentHashMap<>();

    private final Queue<Animation> pending = new ConcurrentLinkedQueue<>();

    private AnimationEngine(Plugin plugin) {

        new BukkitRunnable() {

            @Override
            public void run() {
                tick();
            }

        }.runTaskTimer(plugin, 0L, 1L);
    }

    AnimationHandle register(Animation animation) {

        pending.add(animation);

        return new AnimationHandle(animation);
    }

    private void tick() {

        Animation a;

        while ((a = pending.poll()) != null)
            registerNow(a);

        if (active.isEmpty())
            return;

        List<Animation> sorted = new ArrayList<>(active);

        sorted.sort(Comparator.comparingInt(Animation::priority).reversed());

        for (Animation animation : sorted) {

            if (!animation.tick())
                unregister(animation);
        }
    }

    private void registerNow(Animation animation) {

        active.add(animation);

        if (animation.owner() != null)
            owners.computeIfAbsent(animation.owner(),
                    k -> ConcurrentHashMap.newKeySet()).add(animation);

        if (animation.group() != null)
            groups.computeIfAbsent(animation.group(),
                    k -> ConcurrentHashMap.newKeySet()).add(animation);
    }

    private void unregister(Animation animation) {

        active.remove(animation);

        if (animation.owner() != null) {

            Set<Animation> set = owners.get(animation.owner());

            if (set != null) {

                set.remove(animation);

                if (set.isEmpty())
                    owners.remove(animation.owner());
            }
        }

        if (animation.group() != null) {

            Set<Animation> set = groups.get(animation.group());

            if (set != null) {

                set.remove(animation);

                if (set.isEmpty())
                    groups.remove(animation.group());
            }
        }
    }

    public void cancelOwner(UUID owner) {

        Set<Animation> set = owners.remove(owner);

        if (set == null)
            return;

        for (Animation a : set) {

            a.cancel();
            active.remove(a);
        }
    }

    public void cancelGroup(String group) {

        Set<Animation> set = groups.remove(group);

        if (set == null)
            return;

        for (Animation a : set) {

            a.cancel();
            active.remove(a);
        }
    }

    public void cancelAll() {

        for (Animation a : active)
            a.cancel();

        active.clear();
        owners.clear();
        groups.clear();
    }
}
