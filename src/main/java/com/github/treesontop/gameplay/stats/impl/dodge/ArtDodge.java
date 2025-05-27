package com.github.treesontop.gameplay.stats.impl.dodge;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class ArtDodge extends BasicLender implements IStaticStats, IDodge {
    public static final ArtDodge none = new ArtDodge(0);

    public ArtDodge(float max) {
        super(max);
    }

    @Override
    public boolean dodged(StatsSnapshot source, StatsSnapshot self) {
        var dodge = self.profile().artDodge().value();
        dodge -= (float) Math.pow(source.profile().accuracy().value(), 0.5f);
        return (float) Math.random() * 100 < dodge;
    }

    @Override
    public float value() {
        return maxInRange();
    }
}
