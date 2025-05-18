package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.gameplay.entity.IGameEntity;
import com.github.treesontop.gameplay.stats.IStatsProfile;
import com.github.treesontop.user.User;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntityAttackEvent;

@RegisterEvent
public class PlayerAttack extends EventBase<EntityAttackEvent> {
    public Result execute(EntityAttackEvent event) {
        if (!(event.getEntity() instanceof Player p)) { return Result.SUCCESS; }
        if (event.getTarget() instanceof Player) { return Result.SUCCESS; }

        var entity = IGameEntity.get(event.getTarget());
        if (!(entity instanceof IStatsProfile enemy)) return Result.SUCCESS;
        User.find(p).character.attack(enemy);

        return Result.SUCCESS;
    }
}

