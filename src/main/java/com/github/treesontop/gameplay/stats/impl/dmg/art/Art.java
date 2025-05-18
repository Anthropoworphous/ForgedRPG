package com.github.treesontop.gameplay.stats.impl.dmg.art;

import com.github.treesontop.gameplay.stats.StatsRange;
import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;

public class Art extends BasicLender implements IStaticStat, IAttack {
    public static final Art none = new Art(new StatsRange(0));

    public Art(StatsRange range) {
        super(range);
    }

    @Override
    public void damage(StatsSnapshot source, StatsSnapshot target) {
        var p = target.profile();
        p.hp().consume(target, p.res().tank(source, target, source.profile().art().randomInRange()));
    }
}
