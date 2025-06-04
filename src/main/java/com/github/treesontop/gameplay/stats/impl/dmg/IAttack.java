package com.github.treesontop.gameplay.stats.impl.dmg;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;

public interface IAttack extends IStats {
    @Override
    default StatsRange range() {
        return leaseManager().initialRange();
    }

    float damage(StatsSnapshot source, StatsSnapshot target);

    @SuppressWarnings("unchecked")
    default <T extends IAttack> T noAttack(Class<T> clazz) {
        try {
            return (T) clazz.getField("none").get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
