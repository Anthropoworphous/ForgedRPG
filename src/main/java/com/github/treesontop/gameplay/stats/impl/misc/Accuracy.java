package com.github.treesontop.gameplay.stats.impl.misc;

import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;

public class Accuracy extends BasicLender implements IStaticStats {
    public static Accuracy none = new Accuracy(0);

    public Accuracy(float max) {
        super(max);
    }

    @Override
    public float value() {
        return maxInRange();
    }
}
