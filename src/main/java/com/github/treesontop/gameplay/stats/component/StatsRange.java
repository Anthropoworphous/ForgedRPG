package com.github.treesontop.gameplay.stats.component;

public class StatsRange {
    private final float min, max;

    public static final StatsRange to1 = new StatsRange(0, 1);
    public static final StatsRange to100 = new StatsRange(0, 100);

    public StatsRange(float min, float max) {
        this.min = min;
        this.max = max;
    }
    public StatsRange(float of) {
        this.min = of;
        this.max = of;
    }

    public float min() {
        return min;
    }
    public float max() {
        return max;
    }

    public float diff() { return max - min; }

    public float random() {
        return ((float) Math.random()) * diff() + min;
    }

    public float cap(float value) {
        if (value < min()) {
            //TODO: lowest event call
            return min();
        } else if (value > max()) {
            //TODO: highest event call
            return max();
        }
        return value;
    }
}
