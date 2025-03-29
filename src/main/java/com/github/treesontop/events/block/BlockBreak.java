package com.github.treesontop.events.block;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerBlockBreakEvent;

@RegisterEvent
public class BlockBreak extends EventBase<PlayerBlockBreakEvent> {
    @Override
    public Result execute(PlayerBlockBreakEvent event) {
        event.setCancelled(true);
        return Result.SUCCESS;
    }
}
