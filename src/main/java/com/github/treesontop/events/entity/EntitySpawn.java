package com.github.treesontop.events.entity;

import com.github.treesontop.Main;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.gameplay.entity.IGameEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.entity.EntitySpawnEvent;



@RegisterEvent
public class EntitySpawn extends EventBase<EntitySpawnEvent> {


    @Override
    public Result execute(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player) return Result.SUCCESS;

        var tag = event.getEntity().getTag(IGameEntity.gameEntityTag);

        Main.logger.info("Spawning entity: " + tag);

        if ("INVALID".equals(tag)) throw new RuntimeException("Invalid entity");

        return Result.SUCCESS;
    }
}
