package com.github.treesontop.gameplay.stats.impl.dmg;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class TrueDamage extends BasicLender implements IStaticStat, IAttack {
    public static final TrueDamage none = new TrueDamage(new StatsRange(0));

    public TrueDamage(StatsRange max) {
        super(max);
    }

    @Override
    public float damage(StatsSnapshot source, StatsSnapshot target) {
        return source.profile().trueDamage().randomInRange();
    }
}
