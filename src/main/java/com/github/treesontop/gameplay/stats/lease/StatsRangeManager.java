package com.github.treesontop.gameplay.stats.lease;

import com.github.treesontop.gameplay.stats.component.StatsRange;

public class StatsRangeManager {
    private final StatsRange initialRange;
    private StatsRange range;
    private float debt = 0;

    public StatsRangeManager(StatsRange range) {
        initialRange = range;
        this.range = range;
    }

    public float lease(float goal) {
        goal = Math.min(goal, available());
        debt += goal;
        update();
        return goal;
    }

    public boolean fullCoverage(float goal) {
        return available() > goal;
    }

    public void recover(float value) {
        debt -= value;
        update();
    }

    public float available() {
        return range.max() - range.min();
    }

    public StatsRange initial() {
        return initialRange;
    }

    public StatsRange range() {
        return range;
    }

    private void update() {
        range = new StatsRange(initialRange.min(), initialRange.max() - debt);
    }
}
