package com.github.treesontop.user;

import com.github.treesontop.Util;
import com.github.treesontop.gameplay.inventory.equipment.IPlayerWearer;
import com.github.treesontop.gameplay.stats.IStatsProfile;
import com.github.treesontop.gameplay.stats.StatsRange;
import com.github.treesontop.gameplay.stats.StatsSnapshot;
import com.github.treesontop.gameplay.stats.holder.StatsHolder;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;

public class Character extends StatsHolder implements IStatsProfile.IDefaultStatsProfile, IPlayerWearer {
    private final User user;

    private final Util.Cache<Blunt> blunt;
    private final Util.Cache<Art> art;

    public Character(User user) {
        this.user = user;
        blunt = new Util.Cache<>(() -> weapon().dmgAttribute(Blunt.class).orElse(new Blunt(new StatsRange(0))));
        art = new Util.Cache<>(() -> weapon().dmgAttribute(Art.class).orElse(new Art(new StatsRange(0))));
        init(new StatsSnapshot(this));
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public Blunt blunt() {
        return blunt.value();
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
