package com.github.treesontop.gameplay.stats;

import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.lease.IStatsLeaseManager;
import com.github.treesontop.gameplay.stats.lease.StatsBorrower;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

//NOTE: snapshot.value().get() should only be call with the caller itself.
public interface IStats {
    IStatsLeaseManager leaseManager();

    StatsRange range();

    default float defaultValue() {
        return range().max();
    }

    /**
     * Reduce value of stats base on stats and input
     * @param self the snapshot of the stats owner
     * @param value input, not to be confused with the value of stats
     * @return new stats value
     */
    @CanIgnoreReturnValue
    float consume(StatsSnapshot self, float value);
    /**
     * Increase value of stats base on stats and input
     * @param self the snapshot of the stats owner
     * @param value input, not to be confused with the value of stats
     * @return new stats value
     */
    @CanIgnoreReturnValue
    float provide(StatsSnapshot self, float value);

    default boolean lease(StatsBorrower borrower) {
        return leaseManager().lease(borrower);
    }
}
