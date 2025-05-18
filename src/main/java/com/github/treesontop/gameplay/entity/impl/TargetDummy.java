package com.github.treesontop.gameplay.entity.impl;

import com.github.treesontop.gameplay.entity.IGameEntity;
import com.github.treesontop.gameplay.entity.RegisterEntity;
import com.github.treesontop.gameplay.stats.IStatsProfile;
import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.holder.StatsHolder;
import com.github.treesontop.gameplay.stats.impl.def.Armor;
import com.github.treesontop.gameplay.stats.impl.def.Resistance;
import com.github.treesontop.gameplay.stats.impl.heal.regen.HealthRegen;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Instance;

import static com.github.treesontop.gameplay.entity.impl.TargetDummy.tag;

@RegisterEntity(tag)
public class TargetDummy extends StatsHolder implements IGameEntity, IStatsProfile.IUnkillableProfile, IStatsProfile.INoAttackProfile {
    public static final String tag = "dummy";

    @Override
    public Entity spawn(Instance instance, Pos pos) {
        var result = IGameEntity.super.spawn(instance, pos);
        init(new StatsSnapshot(this));
        return result;
    }

    @Override
    public Armor armor() {
        return Armor.none;
    }

    @Override
    public Resistance res() {
        return Resistance.none;
    }

    @Override
    public HealthRegen hp_r() {
        return HealthRegen.none;
    }

    @Override
    public String tag() {
        return tag;
    }

    @Override
    public EntityType type() {
        return EntityType.ARMOR_STAND;
    }

    @Override
    public EntityFactory factory() {
        return new EntityFactory() {
            @Override
            public IGameEntity create() {
                return new TargetDummy();
            }
        };
    }
}
