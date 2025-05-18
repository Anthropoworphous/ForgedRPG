package com.github.treesontop.gameplay.stats;

public class StatsBuilder {
    public final float initialValue; //scalable
    private float scalableValue = 0;
    private float valueScale = 1;
    private float valueScaleScale = 1;
    private float additionalValue = 0;
    private float finalScale = 1;
    private float nonScalableValue = 0;

    public StatsBuilder(float initialValue) {
        this.initialValue = initialValue;
        scalableValue = initialValue;
    }

    public StatsBuilder addScalableStat(float stat) {
        scalableValue += stat;
        return this;
    }
    public StatsBuilder addStatScale(float scale) {
        valueScale += scale;
        return this;
    }
    public StatsBuilder addStatScaleScale(float scale) {
        valueScaleScale += scale;
        return this;
    }
    public StatsBuilder addStat(float stat) {
        additionalValue += stat;
        return this;
    }
    public StatsBuilder addFinalScale(float scale) {
        finalScale += scale;
        return this;
    }
    public StatsBuilder addNonscalableValue(float stat) {
        nonScalableValue += stat;
        return this;
    }

    public float build() {
        return finalScale * (initialValue * valueScale * valueScaleScale + additionalValue) + nonScalableValue;
    }
}
