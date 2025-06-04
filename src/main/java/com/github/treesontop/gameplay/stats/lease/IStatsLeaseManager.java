package com.github.treesontop.gameplay.stats.lease;

import com.github.treesontop.gameplay.stats.component.StatsRange;

public interface IStatsLeaseManager {
    boolean lease(StatsBorrower client);
    void recover(StatsBorrower client);
    StatsRange initialRange();
    StatsRange currentRange();

}
