package com.github.treesontop.gameplay.stats.impl.dodge;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;

public interface IDodge extends IStats {
    boolean dodged(StatsSnapshot source, StatsSnapshot self);
}
