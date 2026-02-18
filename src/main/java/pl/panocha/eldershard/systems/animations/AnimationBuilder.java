package pl.panocha.eldershard.systems.animations;

import java.util.UUID;

public final class AnimationBuilder {

    private UUID owner;
    private String group;
    private int priority = AnimationPriority.NORMAL.value();

    private int duration;

    private AnimationTick tickConsumer;

    private AnimationBuilder next;

    private AnimationBuilder() {}

    public static AnimationBuilder create() {
        return new AnimationBuilder();
    }

    public AnimationBuilder owner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public AnimationBuilder group(String group) {
        this.group = group;
        return this;
    }

    public AnimationBuilder priority(AnimationPriority priority) {
        this.priority = priority.value();
        return this;
    }

    public AnimationBuilder duration(int ticks) {
        this.duration = ticks;
        return this;
    }

    public AnimationBuilder onTick(AnimationTick consumer) {
        this.tickConsumer = consumer;
        return this;
    }

    public AnimationBuilder then(AnimationBuilder next) {
        this.next = next;
        return this;
    }

    public AnimationHandle start() {
        Animation animation = build();
        return AnimationEngine.get().register(animation);
    }

    Animation build() {

        Animation nextAnim = next != null
                ? next.build()
                : null;

        return new Animation(
                owner,
                group,
                priority,
                duration,
                tickConsumer,
                nextAnim
        );
    }
}
