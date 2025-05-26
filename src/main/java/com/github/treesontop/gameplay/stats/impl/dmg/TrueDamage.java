package com.github.treesontop.gameplay.stats.impl.dmg;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;
import com.github.treesontop.gameplay.stats.impl.hp.Health;

public class TrueDamage extends BasicLender implements IStaticStat, IAttack {
    public TrueDamage(float max) {
        super(max);
    }

    @Override
    public float damage(StatsSnapshot source, StatsSnapshot target) {
        return source.profile().trueDamage().randomInRange();
    }
}
