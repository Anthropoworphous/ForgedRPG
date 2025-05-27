package com.github.treesontop.gameplay.stats.holder.profiles;

import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.impl.dmg.TrueDamage;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;
import com.github.treesontop.gameplay.stats.impl.misc.Accuracy;

public interface INoAttackProfile extends IStatsProfile {
    @Override
    default Art art() {
        return Art.none;
    }

    @Override
    default TrueDamage trueDamage() {
        return TrueDamage.none;
    }

    @Override
    default Slash slash() {
        return Slash.none;
    }

    @Override
    default Blunt blunt() {
        return Blunt.none;
    }

    @Override
    default Puncture puncture() {
        return Puncture.none;
    }

    @Override
    default Pierce pierce() {
        return Pierce.none;
    }

    @Override
    default Tenacity tenacity() {
        return Tenacity.none;
    }

    @Override
    default Accuracy accuracy() { return Accuracy.none; }
}
