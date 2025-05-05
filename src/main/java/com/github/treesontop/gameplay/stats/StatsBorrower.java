package com.github.treesontop.gameplay.stats;

public class StatsBorrower {
    public final float requested;
    public final boolean allOrNothing;

    private float obtained;


    public StatsBorrower(float requested, boolean allOrNothing) {
        this.requested = requested;
        this.allOrNothing = allOrNothing;
    }

    public void accuire(float value) {
        obtained = value;
    }
}
