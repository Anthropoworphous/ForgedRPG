package com.github.treesontop.gameplay.stats.impl;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.StatsSnapshot;

public interface IStaticStat extends IStats {
    @Override
    default float provide(StatsSnapshot self, float value) {
        return range().max();
    }

    @Override
    default float consume(StatsSnapshot self, float value) {
        return range().max();
    }

    @Override
    default boolean needValue() {
        return false;
    }

    default float randomInRange() { return range().random(); }
    default float maxInRange() { return range().max(); }
}
