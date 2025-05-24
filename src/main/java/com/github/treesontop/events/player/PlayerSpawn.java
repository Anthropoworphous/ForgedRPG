package com.github.treesontop.events.player;

import com.github.treesontop.Main;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.user.User;
import net.minestom.server.event.player.PlayerSpawnEvent;

import java.sql.SQLException;


@RegisterEvent
public class PlayerSpawn extends EventBase<PlayerSpawnEvent> {


    @Override
    public Result execute(PlayerSpawnEvent event) {
        if (!event.isFirstSpawn()) return Result.SUCCESS;
        try {
            User.load(event.getPlayer()).msg("Profile loaded!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Main.logger.info(event.getPlayer().getUsername() + " joined the server");
        return Result.SUCCESS;
    }
}
