package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@RegisterEvent
public class PlayerSpawn extends EventBase<PlayerSpawnEvent> {
    @Override
    public Result execute(PlayerSpawnEvent event) {
        if (!event.isFirstSpawn()) return Result.SUCCESS;
        //TODO: load player data
        return Result.SUCCESS;
    }
}
