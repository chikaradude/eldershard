package pl.panocha.eldershard.systems.animations;

public final class AnimationHandle {

    private final Animation animation;

    AnimationHandle(Animation animation) {
        this.animation = animation;
    }

    public void cancel() {
        animation.cancel();
    }

    public boolean isRunning() {
        return animation.isRunning();
    }
}
