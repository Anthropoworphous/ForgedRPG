package com.github.treesontop.gameplay.stats.impl.dodge;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;


public class PhysicDodge extends BasicLender implements IStaticStats, IDodge {
    public static final PhysicDodge none = new PhysicDodge(0);

    public PhysicDodge(float max) {
        super(max);
    }

    @Override
    public boolean dodged(StatsSnapshot source, StatsSnapshot self) {
        var dodge = self.profile().physicDodge().value();
        dodge -= (float) Math.pow(source.profile().accuracy().value(), 0.5f);
        return ((float) Math.random()) * 100f < dodge;
    }

    @Override
    public float value() {
        return maxInRange();
    }
}
