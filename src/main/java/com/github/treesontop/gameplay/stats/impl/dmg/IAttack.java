package com.github.treesontop.gameplay.stats.impl.dmg;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;

public interface IAttack extends IStats {
    @Override
    default StatsRange range() {
        return leaseManager().initialRange();
    }

    float damage(StatsSnapshot source, StatsSnapshot target);
}
