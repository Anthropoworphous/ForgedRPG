package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.gameplay.stats.impl.def.Armor;
import com.github.treesontop.gameplay.stats.impl.def.Resistance;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.heal.regen.HealthRegen;
import com.github.treesontop.gameplay.stats.impl.hp.Health;

/**
 * Hold every possible stats
 */
public interface IStatsProfile {
    default void attack(IStatsProfile target) {
        var source = snapshot();
        var receiver = target.snapshot();
        blunt().damage(source, receiver);
        art().damage(source, receiver);
        accept(source);
        target.accept(receiver);
    }

    StatsSnapshot snapshot();
    void accept(StatsSnapshot current);

    // health
    Health hp();

    // defence
    Armor armor();
    Resistance res();

    // regen
    HealthRegen hp_r();

    // atk
    // L___phy
    Blunt blunt();
    // L___art
    Art art();
    // L___penetration
    //     L___phy
    Pierce pierce();
    Puncture puncture();
    //     L___art
    Tenacity tenacity();

    public interface IDefaultStatsProfile extends IStatsProfile {
        @Override
        default Health hp() {
            return new Health(100);
        }

        @Override
        default Armor armor() {
            return new Armor(0);
        }

        @Override
        default Resistance res() {
            return new Resistance(0);
        }

        @Override
        default HealthRegen hp_r() {
            return new HealthRegen(5);
        }
    }

    public interface IUnkillableProfile extends IStatsProfile {
        @Override
        default Health hp() {
            return new Health.InfiniteHP();
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
        default HealthRegen hp_r() {
            return HealthRegen.none;
        }
    }

    public interface INoAttackProfile extends IStatsProfile {
        @Override
        default Art art() {
            return Art.none;
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
    }
}
