package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class Slash extends BasicLender implements IPhysical, IStaticStat {
    public static final Slash none = new Slash(new StatsRange(0));

    public Slash(StatsRange range) {
        super(range);
    }

    @Override
    public float damage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        return p.armor().tank(source, target, source.profile().puncture().randomInRange());
    }
}
