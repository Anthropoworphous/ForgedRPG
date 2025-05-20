package com.github.treesontop.gameplay.stats.impl.def;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class Armor extends BasicLender implements IStaticStat, IDefence {
    public static final Armor none = new Armor(0);

    public Armor(float max) {
        super(max);
    }

    @Override
    public float tank(StatsSnapshot source, StatsSnapshot self, float dmg) {
        var armor = source.profile().pierce().penetrate(source, self.profile().armor().maxInRange());
        return Math.max(0, dmg - armor);
    }
}
