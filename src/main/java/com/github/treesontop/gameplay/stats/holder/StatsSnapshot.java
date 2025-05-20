package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.gameplay.stats.IStats;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;

public record StatsSnapshot(
    IStatsProfile profile,
    Map<Class<? extends IStats>, Float> value
) {
    private static final Logger logger = Logger.getGlobal();

    public StatsSnapshot(IStatsProfile profile) {
        this(profile, new HashMap<>());
        for (Method m : profile.getClass().getMethods()) {
            var returnType = m.getReturnType();
            if (m.getParameterCount() != 0 || !IStats.class.isAssignableFrom(returnType)) continue;

            try {
                var stats = (IStats) m.invoke(profile);
                if (!stats.needValue()) continue;
                var def = stats.defaultValue();
                //Should be checked already by the isAssignableFrom statement
                //noinspection unchecked
                value.put((Class<? extends IStats>) returnType, def);
                logger.info("%s default to %s".formatted(m.getName(), def));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public float get(Class<? extends IStats> type) {
        return value.get(type);
    }

    public void set(Class<? extends IStats> type, float newValue) {
        value.put(type, newValue);
    }

    /**
     * @return the new value or null if no mapping present
     */
    public Float add(Class<? extends IStats> type, float toAdd) {
        return value.computeIfPresent(type, (ignored, old) -> old + toAdd);
    }

    /**
     * @return the old value
     */
    @CanIgnoreReturnValue
    public float edit(Class<? extends IStats> type, Function<Float, Float> editor) {
        var old = value.get(type);
        value.put(type, editor.apply(old));
        return old;
    }
}
