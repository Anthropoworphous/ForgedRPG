package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class Blunt extends BasicLender implements IStaticStat, IPhysical {
    public static final Blunt none = new Blunt(new StatsRange(0));

    public Blunt(StatsRange range) {
        super(range);
    }

    @Override
    public void damage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        p.hp().consume(target, p.armor().tank(source, target, source.profile().blunt().randomInRange()));
    }
}
