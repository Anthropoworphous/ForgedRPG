package com.github.treesontop.gameplay.stats.holder.profiles;

import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.impl.def.Armor;
import com.github.treesontop.gameplay.stats.impl.def.Resistance;
import com.github.treesontop.gameplay.stats.impl.dodge.ArtDodge;
import com.github.treesontop.gameplay.stats.impl.dodge.PhysicDodge;
import com.github.treesontop.gameplay.stats.impl.heal.regen.HealthRegen;
import com.github.treesontop.gameplay.stats.impl.hp.Health;
import com.github.treesontop.gameplay.stats.impl.hp.Shield;
import com.github.treesontop.gameplay.stats.impl.misc.Accuracy;

public interface IDefaultStatsProfile extends IStatsProfile {
    @Override
    default Health health() {
        return new Health(100);
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
        return new HealthRegen(5);
    }

    @Override
    default Accuracy accuracy() { return Accuracy.none; }
}
