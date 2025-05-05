package com.github.treesontop.gameplay.stats;

import java.util.ArrayList;
import java.util.List;

public class StatLeaseManager {
    private final List<StatsBorrower> clients = new ArrayList<>();
    private final StatRange sourceRange;
    private final StatRange currentRange;

    public StatLeaseManager(StatRange range) {
        sourceRange = range;
        currentRange = new ManagedStatRange(range.min, range.max);
    }

    public boolean lease(StatsBorrower client) {
        if (client.allOrNothing) {
            if (!currentRange.inboundCheck(currentRange.min + client.requested)) return false;
            currentRange.max -= client.requested;
            client.accuire(client.requested);
            return true;
        } else {
            client.accuire(Math.min(client.requested, currentRange.max - currentRange.min));
        }
    }

    public static class ManagedStatRange extends StatRange {
        public ManagedStatRange(float min, float max) {
            super(min, max);
        }
    }
}
