package pl.panocha.eldershard.systems.animations;

public enum AnimationPriority {

    LOW(0),
    NORMAL(10),
    HIGH(50),
    CRITICAL(100);

    private final int value;

    AnimationPriority(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
