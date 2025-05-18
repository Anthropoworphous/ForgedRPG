package com.github.treesontop.events.entity;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.gameplay.entity.IGameEntity;
import net.minestom.server.event.entity.EntityDeathEvent;

public class EntityDeath extends EventBase<EntityDeathEvent> {
    @Override
    public Result execute(EntityDeathEvent event) {
        var entity = event.getEntity();
        var gameEntity = IGameEntity.remove(entity);

        return Result.SUCCESS;
    }
}
