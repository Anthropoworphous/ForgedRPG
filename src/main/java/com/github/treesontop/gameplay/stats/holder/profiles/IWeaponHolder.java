package com.github.treesontop.gameplay.stats.holder.profiles;

import com.github.treesontop.gameplay.inventory.equipment.IEquipmentWearer;
import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;

public interface IWeaponHolder extends IStatsProfile, IEquipmentWearer {
    @Override
    default Slash slash() {
        return weapon().getAttack(Slash.class);
    }
    @Override
    default Blunt blunt() {
        return weapon().getAttack(Blunt.class);
    }
    @Override
    default Puncture puncture() {
        return weapon().getAttack(Puncture.class);
    }
    @Override
    default Art art() {
        return weapon().getAttack(Art.class);
    }
}
