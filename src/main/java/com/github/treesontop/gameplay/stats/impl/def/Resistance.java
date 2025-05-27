package com.github.treesontop.gameplay.stats.impl.def;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class Resistance extends BasicLender implements IStaticStats, IDefence {
    public static final Resistance none = new Resistance(0);

    public Resistance(float max) {
        super(max);
    }

    @Override
    public float tank(StatsSnapshot source, StatsSnapshot self, float dmg) {
        var res = source.profile().tenacity().penetrate(source, self.profile().res().maxInRange());
        res = Math.max(1, (res / 100));
        return dmg * res;
    }
}
