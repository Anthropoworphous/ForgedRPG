package com.github.treesontop.events.block;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerBlockPlaceEvent;

@RegisterEvent
public class BlockPlace extends EventBase<PlayerBlockPlaceEvent> {
    @Override
    public Result execute(PlayerBlockPlaceEvent event) {
        event.setCancelled(true);
        return Result.SUCCESS;
    }
}
