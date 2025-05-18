package com.github.treesontop.gameplay.stats.impl.heal.regen;

import com.github.treesontop.gameplay.stats.StatsCycle;
import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class HealthRegen extends BasicLender implements IStaticStat, IRegen {
    public static final HealthRegen none = new HealthRegen(0);

    public HealthRegen(float max) {
        super(max);
    }

    @Override
    public void regen(StatsSnapshot self) {
        self.profile().hp().provide(self, self.get(HealthRegen.class) * StatsCycle.delta);
    }
}
