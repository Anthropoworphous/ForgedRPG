package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class Puncture extends BasicLender implements IStaticStat, IPhysical {
    public static final Puncture none = new Puncture(new StatsRange(0));

    public Puncture(StatsRange range) {
        super(range);
    }

    @Override
    public float damage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        return p.armor().tank(source, target, source.profile().puncture().randomInRange());
    }
}
