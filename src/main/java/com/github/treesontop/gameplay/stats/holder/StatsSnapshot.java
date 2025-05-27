package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.Main;
import com.github.treesontop.gameplay.stats.IStats;
import com.github.treesontop.gameplay.stats.impl.IStaticStats;
import com.github.treesontop.gameplay.stats.impl.hp.Health;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public record StatsSnapshot(
    IStatsProfile profile,
    Map<Class<? extends IStats>, Float> value
) {
    @SuppressWarnings("unchecked")
    public StatsSnapshot(IStatsProfile profile) {
        this(profile, new HashMap<>());
        for (Method m : profile.getClass().getMethods()) {
            var returnType = m.getReturnType();
            if (m.getParameterCount() != 0 || !IStats.class.isAssignableFrom(returnType)) continue;

            try {
                var stats = (IStats) m.invoke(profile);
                if (stats instanceof IStaticStats) continue; // static stats don't need fluctuating value
                var def = stats.defaultValue();
                //Should be checked already by the isAssignableFrom statement
                value.put((Class<? extends IStats>) returnType, def);
                Main.logger.info("%s default to %s".formatted(m.getName(), def));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void noneStaticCheck(Class<? extends IStats> type) {
        if (IStaticStats.class.isAssignableFrom(type))
            throw new RuntimeException("Attempting to get fluctuating value for static stats");
    }

    public float get(Class<? extends IStats> type) {
        noneStaticCheck(type);
        return value.get(type);
    }

    public void set(Class<? extends IStats> type, float newValue) {
        noneStaticCheck(type);
        value.put(type, newValue);
    }

    /**
     * @return the new value or null if no mapping present
     */
    public Float add(Class<? extends IStats> type, float toAdd) {
        noneStaticCheck(type);
        return value.computeIfPresent(type, (ignored, old) -> old + toAdd);
    }

    /**
     * @return the old value
     */
    @CanIgnoreReturnValue
    public float edit(Class<? extends IStats> type, Function<Float, Float> editor) {
        noneStaticCheck(type);
        var old = value.get(type);
        value.put(type, editor.apply(old));
        return old;
    }



    public boolean isDead() {
        return value.get(Health.class) <= 0;
    }
}
