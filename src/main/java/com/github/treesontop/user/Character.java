package com.github.treesontop.user;

import com.github.treesontop.Util;
import com.github.treesontop.gameplay.inventory.equipment.IPlayerWearer;
import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.holder.StatsHolder;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;

public class Character extends StatsHolder implements IStatsProfile.IDefaultStatsProfile, IPlayerWearer {
    private final User user;

    private final Util.Cache<Slash> slash;
    private final Util.Cache<Blunt> blunt;
    private final Util.Cache<Puncture> puncture;
    private final Util.Cache<Art> art;

    private final Util.Cache.UpdateTrigger updateTrigger;

    public Character(User user) {
        this.user = user;
        blunt = new Util.Cache<>(() -> new Blunt(new StatsRange(weapon().getAttack(Blunt.class))));
        slash = new Util.Cache<>(() -> new Slash(new StatsRange(weapon().getAttack(Slash.class))));
        puncture = new Util.Cache<>(() -> new Puncture(new StatsRange(weapon().getAttack(Puncture.class))));
        art = new Util.Cache<>(() -> new Art(new StatsRange(weapon().getAttack(Art.class))));
        updateTrigger = new Util.Cache.UpdateTrigger(slash, blunt, puncture, art);
        init(new StatsSnapshot(this));
        updateTrigger.update();
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public Slash slash() {
        return slash.value();
    }
    @Override
    public Blunt blunt() {
        return blunt.value();
    }
    @Override
    public Puncture puncture() {
        return puncture.value();
    }
    @Override
    public Art art() {
        return art.value();
    }

    @Override
    public Pierce pierce() {
        return new Pierce(0);
    }


    @Override
    public Tenacity tenacity() {
        return new Tenacity(0);
    }

}
