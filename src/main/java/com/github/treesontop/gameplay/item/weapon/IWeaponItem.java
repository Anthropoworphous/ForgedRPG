package com.github.treesontop.gameplay.item.weapon;

import com.github.treesontop.Main;
import com.github.treesontop.gameplay.item.IGameItem;
import com.github.treesontop.gameplay.item.ItemManager;
import com.github.treesontop.gameplay.stats.holder.StatsDistributor;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.util.Map;


@ItemManager.Register("weapon")
public interface IWeaponItem extends IGameItem {


    StatsDistributor<IAttack> damageDistributor();
    float damage();

    default float getAttack(Class<? extends IAttack> type) {
        var atk = damageDistributor().scale(type, damage());
        Main.logger.info("Scaling dmg type = %s, value: %s".formatted(type.getSimpleName(), atk));
        return atk;
    }

    static IWeaponItem getWeapon(ItemStack raw) {
        var id = raw.getTag(Tag.String("item_id"));
        Main.logger.info(id);
        return ItemManager.getItem(id)
            .filter(item -> item instanceof IWeaponItem)
            .map(item -> (IWeaponItem) item.fromItem(raw))
            .orElse((IWeaponItem) ItemManager.getItem("weapon/fist").orElseThrow());
    }

    //Default
    @ItemManager.Register("weapon/fist")
    class Fist implements IWeaponItem {
        private final StatsDistributor<IAttack> dmgDistro = new StatsDistributor<>(IAttack.class);
        private ItemStack item = null;

        @Override
        public void init() {
            dmgDistro.assign(Map.of(Blunt.class, 1f));
        }

        @Override
        public StatsDistributor<IAttack> damageDistributor() {
            return dmgDistro;
        }

        @Override
        public float damage() {
            return 1;
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
