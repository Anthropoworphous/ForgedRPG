package com.github.treesontop.gameplay.inventory.equipment;

import com.github.treesontop.gameplay.item.weapon.WeaponItem;
import com.github.treesontop.gameplay.stats.holder.IStatsProfile;
import com.github.treesontop.gameplay.stats.holder.profiles.IWeaponHolder;
import com.github.treesontop.user.User;

public interface IPlayerWearer extends IWeaponHolder, IStatsProfile {
    User user();

    @Override
    default WeaponItem weapon() {
        return new WeaponItem(user().getItemInMainHand());
    }
}
