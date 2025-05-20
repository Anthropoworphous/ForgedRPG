package com.github.treesontop.gameplay.item.weapon.sword;

import com.github.treesontop.gameplay.item.IGameItem;
import com.github.treesontop.gameplay.item.ItemManager;
import com.github.treesontop.gameplay.stats.holder.StatsDistributor;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;
import net.minestom.server.item.ItemStack;

import java.util.Map;

@ItemManager.Register("dagger")
public class Dagger implements ISword {
    private final StatsDistributor<IAttack> distributor = new StatsDistributor<>(IAttack.class);

    @Override
    public void init() {
        distributor.assign(Map.of(
            Puncture.class, 0.5f,
            Slash.class, 0.5f
        ));
    }

    @Override
    public StatsDistributor<IAttack> damageDistributor() {
        return distributor;
    }

    @Override
    public float damage() {
        return 5;
    }

    @Override
    public String name() {
        return "Dagger";
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public IGameItem fromItem(ItemStack item) {
        return null;
    }

    @Override
    public ItemStack toItem() {
        return null;
    }
}
