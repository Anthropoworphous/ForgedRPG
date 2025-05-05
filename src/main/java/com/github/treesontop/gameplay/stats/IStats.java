package com.github.treesontop.gameplay.stats;

public interface IStats {
    boolean required();

    StatRange range();
    float consume(float value);
    float provide(float value);

    boolean lease(StatsBorrower borrower);
}
