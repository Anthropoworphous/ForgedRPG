package com.github.treesontop.gameplay.stats.impl.hp;

import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.BasicLender;
import com.github.treesontop.gameplay.stats.lease.StatsBorrower;



public class Health extends BasicLender implements IStats {
    public Health(float max) {
        super(max);
    }

    @Override
    public float consume(StatsSnapshot self, float value) {
        var result = range().cap(self.get(Health.class) - value);
        self.set(Health.class, result);
        return result;
    }

    @Override
    public float provide(StatsSnapshot self, float value) {
        return consume(self, -value);
    }


//TODO: hit for 0???
    public static class InfiniteHP extends Health {
        public InfiniteHP() {
            super(1);
        }

        @Override
        public boolean lease(StatsBorrower borrower) {
            return false;
        }

        @Override
        public float consume(StatsSnapshot self, float value) {
            return 1;
        }

        @Override
        public float provide(StatsSnapshot self, float value) {
            return 1;
        }
    }
}
