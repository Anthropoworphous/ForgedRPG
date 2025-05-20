package com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy;

import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.IPenetration;

public class Pierce extends BasicLender implements IPenetration {
    public static final Pierce none = new Pierce(0);

    public Pierce(float max) {
        super(max);
    }

    @Override
    public float penetrate(StatsSnapshot source, float resistance) {
        var pierce = Math.max(1, 1 - Math.min(0, source.profile().pierce().maxInRange() / 100));
        return resistance * pierce;
    }
}
