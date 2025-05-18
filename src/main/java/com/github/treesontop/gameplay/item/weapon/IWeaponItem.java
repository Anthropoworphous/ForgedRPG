package com.github.treesontop.gameplay.item.weapon;

import com.github.treesontop.gameplay.item.IGameItem;
import com.github.treesontop.gameplay.item.ItemManager;
import com.github.treesontop.gameplay.stats.StatsRange;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.util.Map;
import java.util.Optional;

public interface IWeaponItem extends IGameItem {
    <T extends IAttack> Optional<T> dmgAttribute(Class<T> type);
    Map<Class<? extends IAttack>, IAttack> dmgAttributes();

    static IWeaponItem getWeapon(ItemStack raw) {
        var id = raw.getTag(Tag.String("item_id"));
        return ItemManager.getItem(id)
            .filter(item -> item instanceof IWeaponItem)
            .map(item -> (IWeaponItem) item.fromItem(raw))
            .orElse((IWeaponItem) new Fist().fromItem(raw));
    }

    //Default
    @ItemManager.Register(id = "weapon/fist")
    class Fist implements IWeaponItem {
        private ItemStack item = null;

        @SuppressWarnings("unchecked")
        @Override
        public <T extends IAttack> Optional<T> dmgAttribute(Class<T> type) {
            if (type == Blunt.class) {
                return Optional.of((T) new Blunt(new StatsRange(1, 5)));
            }
            return Optional.empty();
        }

        @Override
        public Map<Class<? extends IAttack>, IAttack> dmgAttributes() {
            return Map.of(Blunt.class, new Blunt(new StatsRange(1, 5)));
        }

        @Override
        public String name() {
            return "Fist";
        }

        @Override
        public boolean isDefault() {
            return item == null;
        }

        @Override
        public IGameItem fromItem(ItemStack item) {
            this.item = item;
            return this;
        }

        @Override
        public ItemStack toItem() {
            return item;
        }
    }
}
