package com.github.treesontop.gameplay.stats.lease;

import com.github.treesontop.gameplay.stats.component.StatsRange;

import java.util.ArrayList;
import java.util.List;

public class StatsLeaseManager implements IStatsLeaseManager {
    private final List<StatsBorrower> clients = new ArrayList<>();
    private final StatsRangeManager currentRange;

    public StatsLeaseManager(StatsRange range) {
        currentRange = new StatsRangeManager(range);
    }

    @Override
    public boolean lease(StatsBorrower client) {
        if (client.allOrNothing && !(currentRange.available() > client.requested)) return false;
        client.acquire(this, currentRange.lease(client.requested));
        return true;
    }

    @Override
    public void recover(StatsBorrower client) {
        if (!clients.remove(client)) throw new RuntimeException("Never borrowed");
        currentRange.recover(client.obtained());
    }

    @Override
    public StatsRange initialRange() {
        return currentRange.initial();
    }

    @Override
    public StatsRange currentRange() {
        return currentRange.range();
    }
}
