package com.github.treesontop.gameplay.stats.impl.hp;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;

public class Shield extends BasicLender implements IStats {
    public Shield(float value) {
        super(value);
    }

    @Override
    public float consume(StatsSnapshot self, float value) {
        return self.add(BasicLender.class, -value);
    }

    @Override
    public float provide(StatsSnapshot self, float value) {
        return self.add(BasicLender.class, value);
    }
}
