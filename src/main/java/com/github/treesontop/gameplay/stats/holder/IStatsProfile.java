package com.github.treesontop.gameplay.stats.holder;

import com.github.treesontop.Main;
import com.github.treesontop.gameplay.stats.cycle.ICycleListener;
import com.github.treesontop.gameplay.stats.impl.def.Armor;
import com.github.treesontop.gameplay.stats.impl.def.Resistance;
import com.github.treesontop.gameplay.stats.impl.dmg.TrueDamage;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;
import com.github.treesontop.gameplay.stats.impl.dodge.ArtDodge;
import com.github.treesontop.gameplay.stats.impl.dodge.PhysicDodge;
import com.github.treesontop.gameplay.stats.impl.heal.regen.HealthRegen;
import com.github.treesontop.gameplay.stats.impl.hp.Health;
import com.github.treesontop.gameplay.stats.impl.hp.Shield;
import com.github.treesontop.gameplay.stats.impl.misc.Accuracy;

/**
 * Hold every possible stats
 */
public interface IStatsProfile extends ICycleListener {
    default void attack(IStatsProfile target) {
        var source = snapshot();
        var receiver = target.snapshot();
        var dmg = 0f;
        dmg += slash().damage(source, receiver);
        dmg += blunt().damage(source, receiver);
        dmg += puncture().damage(source, receiver);
        dmg += art().damage(source, receiver);

        target.lossHealth(dmg);
        Main.logger.info("dealt %s damage".formatted(dmg));

        accept(source);
        target.accept(receiver);
    }

    default void lossHealth(float value) {
        var snap = snapshot();
        health().consume(snap, value);
        accept(snap);

        if (snap.isDead()) {
            death();
        }
    }

    @Override
    default void onCycle() {
        var snap = snapshot();
        healthRegen().onCycle(snap);
    }

    void death();

    StatsSnapshot snapshot();
    void accept(StatsSnapshot current);

    // health
    Health health();
    Shield shield();

    // defence
    Armor armor();
    Resistance res();
    // L___dodge
    PhysicDodge physicDodge();
    ArtDodge artDodge();

    // regen
    HealthRegen healthRegen();

    // atk
    // L___phy
    TrueDamage trueDamage();
    Slash slash();
    Blunt blunt();
    Puncture puncture();
    // L___art
    Art art();
    // L___penetration
    //     L___phy
    Pierce pierce();
    //     L___art
    Tenacity tenacity();

    // misc
    Accuracy accuracy();
}
