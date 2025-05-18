package com.github.treesontop.gameplay.inventory.equipment;

import com.github.treesontop.gameplay.item.weapon.IWeaponItem;
import com.github.treesontop.gameplay.stats.IStatsProfile;
import com.github.treesontop.user.User;

public interface IPlayerWearer extends IEquipmentWearer, IStatsProfile {
    User user();

    @Override
    public default IWeaponItem weapon() {
        return IWeaponItem.getWeapon(user().player.getItemInMainHand());
    }
}
