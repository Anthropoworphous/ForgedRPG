package com.github.treesontop.gameplay.stats.impl.heal.regen;

import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.heal.IHealing;

public interface IRegen extends IHealing {
    void regen(StatsSnapshot self);
}
