package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;

@RegisterEvent
public class PlayerDisconnect extends EventBase<PlayerDisconnectEvent> {
    @Override
    public Result execute(PlayerDisconnectEvent event) {

        return Result.SUCCESS;
    }
}
