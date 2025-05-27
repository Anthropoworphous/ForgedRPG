package com.github.treesontop.gameplay.stats.cycle;

import net.minestom.server.timer.Scheduler;
import net.minestom.server.timer.TaskSchedule;

import java.util.HashSet;
import java.util.Set;

public class StatsCycle {
    public static final float delta = 0.05f;

    private static final Set<ICycleListener> listeners = new HashSet<>();

    public static void listen(ICycleListener listener) {
        listeners.add(listener);
    }
    public static void stop(ICycleListener listener) {
        listeners.remove(listener);
    }

    public static void startCycle() {
        Scheduler.newScheduler().scheduleTask(() -> {
            if (listeners.isEmpty()) return;
            listeners.forEach(ICycleListener::onCycle);
        }, TaskSchedule.immediate(), TaskSchedule.tick(1));
    }
}
