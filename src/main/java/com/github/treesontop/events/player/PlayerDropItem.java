package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.item.ItemDropEvent;

@RegisterEvent
public class PlayerDropItem extends EventBase<ItemDropEvent> {
    @Override
    public Result execute(ItemDropEvent event) {
        event.setCancelled(true);
        return Result.SUCCESS;
    }

//    @Override
//    public Result execute(ItemDropEvent event) {
//        var item = event.getItemStack();
//        var player = event.getPlayer();
//        var pPos = player.getPosition();
//        var entity = new ItemEntity(item);
//        entity.setPickupDelay(3, ChronoUnit.SECONDS);
//        var rotation = Quaternion.fromEuler(Math.toRadians(-pPos.yaw()), Math.toRadians(pPos.pitch()), 0);
//        var force = rotation.apply(new Vec(0, 0, 10));
//        entity.setVelocity(force);
//        entity.setInstance(event.getInstance(), player.getPosition()
//                .add(0, 0.5, 0)
//                .withPitch(0));
//        entity.synchronizeNextTick();
//        return Result.SUCCESS;
//    }
}
