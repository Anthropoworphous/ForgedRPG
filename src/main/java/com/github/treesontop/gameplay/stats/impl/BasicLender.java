package com.github.treesontop.gameplay.stats.impl;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.lease.IStatsLeaseManager;
import com.github.treesontop.gameplay.stats.lease.StatsLeaseManager;

public abstract class BasicLender implements IStats {
    private final StatsLeaseManager manager;

    public BasicLender(float max) {
        this(new StatsRange(0, max));
    }
    public BasicLender(StatsRange range) {
        manager = new StatsLeaseManager(range);
    }

    @Override
    public IStatsLeaseManager leaseManager() {
        return manager;
    }

    @Override
    public StatsRange range() {
        return manager.currentRange();
    }
}
