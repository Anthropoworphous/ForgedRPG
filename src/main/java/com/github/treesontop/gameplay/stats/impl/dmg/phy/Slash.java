package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class Slash extends Physical implements IStaticStats {
    public static final Slash none = new Slash(new StatsRange(0));

    public Slash(StatsRange range) {
        super(range);
    }

    @Override
    public float physicalDamage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        return p.armor().tank(source, target, source.profile().puncture().value());
    }
}
