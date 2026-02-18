package pl.panocha.eldershard.systems.animations;

@FunctionalInterface
public interface AnimationTick {

    void tick(int tick, double progress);
}
