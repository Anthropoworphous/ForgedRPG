package com.github.treesontop.gameplay.stats.component;

public record StatsRange(float min, float max) {
    public static final StatsRange none = new StatsRange(0);

    public StatsRange(float of) {
        this(of, of);
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
