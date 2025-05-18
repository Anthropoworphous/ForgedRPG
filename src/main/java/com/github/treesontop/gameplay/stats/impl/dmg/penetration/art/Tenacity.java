package com.github.treesontop.gameplay.stats.impl.dmg.penetration.art;

import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.IPenetration;

public class Tenacity extends BasicLender implements IPenetration {
    public static final Tenacity none = new Tenacity(0);

    public Tenacity(float max) {
        super(max);
    }

    @Override
    public float penetrate(StatsSnapshot source, float resistance) {
        return Math.min(0, resistance - source.profile().tenacity().maxInRange());
    }
}

