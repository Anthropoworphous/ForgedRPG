package com.github.treesontop.gameplay.stats.lease;

public class StatsBorrower {
    public final float requested;
    public final boolean allOrNothing;

    private IStatsLeaseManager source;
    private float obtained = 0;


    public StatsBorrower(float requested, boolean allOrNothing) {
        this.requested = requested;
        this.allOrNothing = allOrNothing;
    }

    public void acquire(IStatsLeaseManager source, float value) {
        this.source = source;
        obtained = value;
    }

    public void repay() {
        source.recover(this);
    }

    public float obtained() {
        return obtained;
    }
}
