package com.github.treesontop.gameplay.stats.impl;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.StatRange;
import com.github.treesontop.gameplay.stats.StatsBorrower;

public class Health implements IStats {
    private final StatRange range;
    private float value;

    public Health(StatRange range) {
        this.range = range;
    }

    @Override
    public boolean required() { return true; }
    @Override
    public StatRange range() { return range; }

    @Override
    public float consume(float value) {
        return this.value -= value;
    }

    @Override
    public float provide(float value) {
        return this.value += value;
    }

    @Override
    public boolean lease(StatsBorrower borrower) {
        return false;
    }
}
