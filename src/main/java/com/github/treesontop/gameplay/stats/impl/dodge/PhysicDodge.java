package com.github.treesontop.gameplay.stats.impl.dodge;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;


public class PhysicDodge extends BasicLender implements IStaticStat, IDodge {
    public static final PhysicDodge none = new PhysicDodge(0);

    public PhysicDodge(float max) {
        super(max);
    }

    @Override
    public boolean dodged(StatsSnapshot source, StatsSnapshot self) {
        return ((float) Math.random()) * 100f < self.profile().physicDodge().maxInRange();
    }
}
