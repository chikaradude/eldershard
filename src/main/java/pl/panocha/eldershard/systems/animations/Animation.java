package pl.panocha.eldershard.systems.animations;

import java.util.UUID;

final class Animation {

    private final UUID owner;
    private final String group;
    private final int priority;

    private final int duration;
    private final AnimationTick tickConsumer;

    private final Animation next;

    private int tick;
    private boolean cancelled;
    private boolean running = true;

    Animation(
            UUID owner,
            String group,
            int priority,
            int duration,
            AnimationTick tickConsumer,
            Animation next
    ) {
        this.owner = owner;
        this.group = group;
        this.priority = priority;
        this.duration = duration;
        this.tickConsumer = tickConsumer;
        this.next = next;
    }

    UUID owner() {
        return owner;
    }

    String group() {
        return group;
    }

    int priority() {
        return priority;
    }

    boolean isRunning() {
        return running && !cancelled;
    }

    void cancel() {
        cancelled = true;
        running = false;
    }

    boolean tick() {

        if (cancelled)
            return false;

        double progress = duration == 0
                ? 1.0
                : Math.min(1.0, (double) tick / duration);

        tickConsumer.tick(tick, progress);

        tick++;

        if (tick > duration) {

            running = false;

            if (next != null)
                AnimationEngine.get().register(next);

            return false;
        }

        return true;
    }
}
