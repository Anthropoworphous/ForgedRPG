package com.github.treesontop.events.block;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerHand;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.item.ItemStack;

@RegisterEvent
public class BlockInteract extends EventBase<PlayerBlockInteractEvent> {
    @Override
    public Result execute(PlayerBlockInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand(PlayerHand.MAIN);

        return Result.SUCCESS;
    }
}
