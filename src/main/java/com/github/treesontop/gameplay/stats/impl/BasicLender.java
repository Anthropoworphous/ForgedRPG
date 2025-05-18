package com.github.treesontop.gameplay.stats.impl;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.StatsRange;
import com.github.treesontop.gameplay.stats.lease.IStatLeaseManager;
import com.github.treesontop.gameplay.stats.lease.StatLeaseManager;

public abstract class BasicLender implements IStats {
    private final StatLeaseManager manager;

    public BasicLender(float max) {
        this(new StatsRange(0, max));
    }
    public BasicLender(StatsRange range) {
        manager = new StatLeaseManager(range);
    }

    @Override
    public IStatLeaseManager leaseManager() {
        return manager;
    }

    @Override
    public StatsRange range() {
        return manager.currentRange();
    }
}
