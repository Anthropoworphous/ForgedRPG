package com.github.treesontop.gameplay.stats.impl.dmg.phy;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;

public abstract class Physical extends BasicLender implements IAttack, IStaticStats {
    public Physical(StatsRange range) {
        super(range);
    }

    @Override
    public float damage(StatsSnapshot source, StatsSnapshot target) {
        if (target.profile().physicDodge().dodged(source, target)) return 0;
        return physicalDamage(source, target);
    }

    @Override
    public float value() {
        return randomInRange();
    }

    abstract float physicalDamage(StatsSnapshot source, StatsSnapshot target);
}
