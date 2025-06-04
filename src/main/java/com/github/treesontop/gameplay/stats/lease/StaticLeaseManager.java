package com.github.treesontop.gameplay.stats.lease;

import com.github.treesontop.gameplay.stats.component.StatsRange;

public class StaticLeaseManager implements IStatsLeaseManager {
    private final StatsRange range;

    private StaticLeaseManager(StatsRange currentRange) {
        range = currentRange;
    }

    @Override
    public boolean lease(StatsBorrower client) {
        return false;
    }

    @Override
    public void recover(StatsBorrower client) {
        throw new RuntimeException("Never borrowed");
    }

    @Override
    public StatsRange initialRange() {
        return range;
    }

    @Override
    public StatsRange currentRange() {
        return range;
    }
}
