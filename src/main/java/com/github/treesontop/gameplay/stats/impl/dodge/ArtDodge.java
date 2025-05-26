package com.github.treesontop.gameplay.stats.impl.dodge;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStat;

public class ArtDodge extends BasicLender implements IStaticStat, IDodge {
    public ArtDodge(float max) {
        super(max);
    }

    @Override
    public boolean dodged(StatsSnapshot source, StatsSnapshot self) {
        return ((float) Math.random()) * 100 < self.profile().artDodge().maxInRange();
    }
}
