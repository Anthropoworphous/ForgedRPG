package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerUseItemEvent;

@RegisterEvent()
public class PlayerUseItem extends EventBase<PlayerUseItemEvent> {
    @Override
    public Result execute(PlayerUseItemEvent event) {

        return Result.SUCCESS;
    }
}
