package com.github.treesontop.gameplay.item.weapon;

import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import org.junit.jupiter.api.Test;

class WeaponItemTest {
    @Test
    void testGetAttack() {
        var dmg = new WeaponItem.Builder(ItemStack.of(Material.STONE))
            .blunt(69, 420)
            .build()
            .getAttack(Blunt.class)
            .value();

        assert dmg <= 420 && dmg >= 69;
        System.out.printf("Item damage calculation within parameter [69/%s/420]%n", dmg);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme
