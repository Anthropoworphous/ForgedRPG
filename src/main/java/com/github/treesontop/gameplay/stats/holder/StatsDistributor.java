package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.Main;
import com.github.treesontop.gameplay.stats.IStats;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class StatsDistributor<T extends IStats> {
    @Nullable
    private final StatsDistributor<? super T> source;
    private final Class<T> classRef;
    private final Map<Class<? extends T>, Float> distribution = new HashMap<>();

    public StatsDistributor(Class<T> classRef) {
        this.classRef = classRef;
        source = null;
    }
    public StatsDistributor(@Nullable StatsDistributor<? super T> source, Class<T> classRef) {
        this.source = source;
        this.classRef = classRef;
    }

    public void assign(Map<Class<? extends T>, Float> configurations) {
        var total = 0f;
        for (var config : configurations.entrySet()) {
            var v = config.getValue();
            total += v;
            distribution.put(config.getKey(), v);
        }
        var scale = 1 / total;
        if (scale == 1) return;
        distribution.replaceAll((k, original) -> original * scale);
    }

    public float scale(Class<? extends T> type, float original) {
        var scale = distribution.get(type);
        if (scale == null) {
            Main.logger.info("Item doesn't have " + type.getSimpleName());
            return 0;
        }
        var value = scale * original;
        if (source != null) {
            if (!distribution.containsKey(type)) return source.scale(type, original);
            value *= source.distribution.get(classRef);
        }
        return value;
    }
}
