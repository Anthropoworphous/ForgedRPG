package com.github.treesontop.events.block;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.item.ItemStack;

import java.util.Objects;

@RegisterEvent
public class BlockInteract extends EventBase<PlayerBlockInteractEvent> {
    @Override
    public Result execute(PlayerBlockInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getItemInHand(Objects.requireNonNullElse(player.getItemUseHand(), Player.Hand.MAIN));

        return Result.SUCCESS;
    }
}
