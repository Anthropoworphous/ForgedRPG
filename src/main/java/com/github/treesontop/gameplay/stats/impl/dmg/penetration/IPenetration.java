package com.github.treesontop.gameplay.stats.impl.dmg.penetration;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public interface IPenetration extends IStaticStats, IStats {
    float penetrate(StatsSnapshot source, float resistance);
}
