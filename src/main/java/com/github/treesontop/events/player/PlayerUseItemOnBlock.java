package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent;

@RegisterEvent
public class PlayerUseItemOnBlock extends EventBase<PlayerUseItemOnBlockEvent> {
    @Override
    public Result execute(PlayerUseItemOnBlockEvent event) {

        return Result.SUCCESS;
    }
}
