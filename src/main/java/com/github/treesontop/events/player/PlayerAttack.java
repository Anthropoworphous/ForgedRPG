package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntityAttackEvent;

@RegisterEvent
public class PlayerAttack extends EventBase<EntityAttackEvent> {
    public Result execute(EntityAttackEvent event) {
        if (!(event.getEntity() instanceof Player p)) { return Result.SUCCESS; }

        return Result.SUCCESS;
    }
}
