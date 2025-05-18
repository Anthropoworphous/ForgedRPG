package com.github.treesontop.gameplay.stats.impl.dmg;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.StatsRange;
import com.github.treesontop.gameplay.stats.StatsSnapshot;

public interface IAttack extends IStats {
    @Override
    default StatsRange range() {
        return leaseManager().initialRange();
    }

    void damage(StatsSnapshot source, StatsSnapshot target);
}
