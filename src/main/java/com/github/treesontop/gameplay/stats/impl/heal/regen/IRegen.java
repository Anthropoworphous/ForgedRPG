package com.github.treesontop.gameplay.stats.impl.heal.regen;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.ICyclingStats;
import com.github.treesontop.gameplay.stats.impl.heal.IHealing;

public interface IRegen extends IHealing, ICyclingStats {
    void regen(StatsSnapshot self);

    @Override
    default void onCycle(StatsSnapshot self) {
        regen(self);
    }
}
