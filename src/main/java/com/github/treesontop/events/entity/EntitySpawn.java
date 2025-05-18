package com.github.treesontop.events.entity;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.gameplay.entity.IGameEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntitySpawnEvent;

import java.util.logging.Logger;

@RegisterEvent
public class EntitySpawn extends EventBase<EntitySpawnEvent> {
    private static final Logger logger = Logger.getGlobal();

    @Override
    public Result execute(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player) return Result.SUCCESS;

        var tag = event.getEntity().getTag(IGameEntity.gameEntityTag);

        logger.info("Spawning entity: " + tag);

        if ("INVALID".equals(tag)) throw new RuntimeException("Invalid entity");

        return Result.SUCCESS;
    }
}
