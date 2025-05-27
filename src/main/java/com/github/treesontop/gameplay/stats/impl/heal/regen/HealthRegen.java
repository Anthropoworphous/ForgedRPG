package com.github.treesontop.gameplay.stats.impl.heal.regen;

import com.github.treesontop.gameplay.stats.cycle.StatsCycle;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class HealthRegen extends BasicLender implements IStaticStats, IRegen {
    public static final HealthRegen none = new HealthRegen(0);

    public HealthRegen(float max) {
        super(max);
    }

    @Override
    public void regen(StatsSnapshot self) {
        self.profile().health().provide(self, self.profile().healthRegen().value() * StatsCycle.delta);
    }

    @Override
    public float value() {
        return maxInRange();
    }
}
