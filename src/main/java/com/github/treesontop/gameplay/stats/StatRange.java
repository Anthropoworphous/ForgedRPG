package com.github.treesontop.gameplay.stats;

public class StatRange {
    public float min, max;

    public StatRange(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public boolean inboundCheck(float value) { return value >= min && value <= max; }
    public float cap(float value) { return Math.clamp(value, min, max); }
}
