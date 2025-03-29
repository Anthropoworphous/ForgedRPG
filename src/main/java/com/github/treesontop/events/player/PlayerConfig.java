package com.github.treesontop.events.player;

import com.github.treesontop.Main;
import com.github.treesontop.World;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;

@RegisterEvent
public class PlayerConfig extends EventBase<AsyncPlayerConfigurationEvent> {
    @Override
    public Result execute(AsyncPlayerConfigurationEvent event) {
        event.setSpawningInstance(World.MAIN.get());
        event.getPlayer().setRespawnPoint(new Pos(0, 50, 0, -90, 0));
        return Result.SUCCESS;
    }
}
