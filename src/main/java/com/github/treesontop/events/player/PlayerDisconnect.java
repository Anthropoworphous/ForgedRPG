package com.github.treesontop.events.player;

import com.github.treesontop.Main;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.user.User;
import net.minestom.server.event.player.PlayerDisconnectEvent;

import java.sql.SQLException;
import java.util.logging.Level;


@RegisterEvent
public class PlayerDisconnect extends EventBase<PlayerDisconnectEvent> {


    @Override
    public Result execute(PlayerDisconnectEvent event) {
        try {
            User.save(event.getPlayer());
        } catch (SQLException e) {
            Main.logger.log(Level.SEVERE, e.getMessage());
        }
        Main.logger.info(event.getPlayer().getUsername() + " left the server");
        return Result.SUCCESS;
    }
}

