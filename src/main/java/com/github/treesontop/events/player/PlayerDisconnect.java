package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.user.User;
import net.minestom.server.event.player.PlayerDisconnectEvent;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RegisterEvent
public class PlayerDisconnect extends EventBase<PlayerDisconnectEvent> {
    private static final Logger logger = Logger.getGlobal();

    @Override
    public Result execute(PlayerDisconnectEvent event) {
        //TODO: NOT SAVING
        try {
            User.save(event.getPlayer());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        logger.info(event.getPlayer().getUsername() + " left the server");
        return Result.SUCCESS;
    }
}
