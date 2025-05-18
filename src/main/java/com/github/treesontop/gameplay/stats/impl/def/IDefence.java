package com.github.treesontop.gameplay.stats.impl.def;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.StatsSnapshot;

public interface IDefence extends IStats {
    float tank(StatsSnapshot source, StatsSnapshot self, float dmg);
}
