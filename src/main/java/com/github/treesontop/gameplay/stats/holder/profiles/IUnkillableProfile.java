package com.github.treesontop.gameplay.stats.holder.profiles;

import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.impl.def.Armor;
import com.github.treesontop.gameplay.stats.impl.def.Resistance;
import com.github.treesontop.gameplay.stats.impl.dodge.ArtDodge;
import com.github.treesontop.gameplay.stats.impl.dodge.PhysicDodge;
import com.github.treesontop.gameplay.stats.impl.heal.regen.HealthRegen;
import com.github.treesontop.gameplay.stats.impl.hp.Health;
import com.github.treesontop.gameplay.stats.impl.hp.Shield;

public interface IUnkillableProfile extends IStatsProfile {
    @Override
    default Health health() {
        return new Health.InfiniteHP();
    }
    @Override
    default Shield shield() {
        return Shield.none;
    }

    @Override
    default Armor armor() {
        return Armor.none;
    }
    @Override
    default Resistance res() {
        return Resistance.none;
    }

    @Override
    default PhysicDodge physicDodge() {
        return PhysicDodge.none;
    }
    @Override
    default ArtDodge artDodge() {
        return ArtDodge.none;
    }

    @Override
    default HealthRegen healthRegen() {
        return HealthRegen.none;
    }
}
