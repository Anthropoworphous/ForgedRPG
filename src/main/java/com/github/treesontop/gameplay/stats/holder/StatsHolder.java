package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.gameplay.stats.IStatsProfile;
import com.github.treesontop.gameplay.stats.StatsSnapshot;

public abstract class StatsHolder implements IStatsProfile {
    private StatsSnapshot snapshot = null;

    public void init(StatsSnapshot snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public void accept(StatsSnapshot current) {
        snapshot = current;
    }

    @Override
    public StatsSnapshot snapshot() {
        if (snapshot == null) throw new RuntimeException("Snapshot not initialized");
        return snapshot;
    }
}
