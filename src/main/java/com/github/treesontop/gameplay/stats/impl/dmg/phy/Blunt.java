package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class Blunt extends Physical implements IStaticStats {
    public static final Blunt none = new Blunt(new StatsRange(0));

    public Blunt(StatsRange range) {
        super(range);
    }

    @Override
    public float physicalDamage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        return p.armor().tank(source, target, source.profile().blunt().randomInRange());
    }
}
