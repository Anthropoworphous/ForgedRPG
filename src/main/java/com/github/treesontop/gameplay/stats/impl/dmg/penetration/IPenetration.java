package com.github.treesontop.gameplay.stats.impl.dmg.penetration;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public interface IPenetration extends IStaticStat, IStats {
    float penetrate(StatsSnapshot source, float resistance);
}
