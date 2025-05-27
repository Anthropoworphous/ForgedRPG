package com.github.treesontop.gameplay.stats.impl.def;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public interface IDefence extends IStaticStats {
    float tank(StatsSnapshot source, StatsSnapshot self, float dmg);

    @Override
    default float value() {
        return maxInRange();
    }
}
