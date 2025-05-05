package com.github.treesontop.commands;

import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.user.User;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;

import java.util.logging.Logger;

@RegisterCommand(name = "money")
public class MoneyCommand extends PlayerOnlyCMDBase {
    private static final Logger logger = Logger.getGlobal();

    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        var money = new ArgumentInteger("money");

        builder.implement((exe, ctx) -> {
            var m = ctx.get(money);
            User.find(exe).money(m);
            logger.info(exe.getUsername() + "'s money have been set to " + m);
        }, money);
        builder.implement((exe, ctx) ->
            logger.info(exe.getUsername() + " have $" + User.find(exe).money()));
    }
}
