package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;

@RegisterEvent
public class PlayerPickupItem extends EventBase<PickupItemEvent> {
    @Override
    public Result execute(PickupItemEvent event) {
        Player player = (Player) event.getEntity();
        ItemStack itemStack = event.getItemStack();

        player.getInventory().addItemStack(itemStack);
        return Result.SUCCESS;

    }
}