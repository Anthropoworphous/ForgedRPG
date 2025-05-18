package com.github.treesontop.gameplay.stats.lease;

import com.github.treesontop.gameplay.stats.StatsRange;

public interface IStatLeaseManager {
    boolean lease(StatsBorrower client);
    void recover(StatsBorrower client);
    StatsRange initialRange();
    StatsRange currentRange();

    public static class ManagedStatsRange extends StatsRange {
        private final StatsRange initialRange;
        private float debt = 0;

        public ManagedStatsRange(StatsRange range) {
            super(range.min(), range.max());
            initialRange = range;
        }

        StatsRange initial() { return initialRange; }

        public float lease(float goal) {
            goal = Math.min(goal, available());
            debt += goal;
            return goal;
        }

        public boolean fullCoverage(float goal) {
            return available() > goal;
        }

        public void recover(float value) {
            debt -= value;
        }

        public float available() {
            return max() - min();
        }

        @Override
        public float max() {
            return super.max() - debt;
        }
    }

}
