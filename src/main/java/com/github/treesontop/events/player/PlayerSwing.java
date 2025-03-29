package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerHandAnimationEvent;

@RegisterEvent
public class PlayerSwing extends EventBase<PlayerHandAnimationEvent> {
    @Override
    public Result execute(PlayerHandAnimationEvent event) {
        Player p = event.getEntity();

        return Result.SUCCESS;
    }
}
