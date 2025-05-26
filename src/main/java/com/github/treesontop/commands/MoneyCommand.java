package com.github.treesontop.commands;

import com.github.treesontop.Main;
import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.user.User;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;



@RegisterCommand("money")
public class MoneyCommand extends PlayerOnlyCMDBase {


    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        var money = new ArgumentInteger("money");

        builder.implement((exe, ctx) -> {
            var m = ctx.get(money);
            User.find(exe).money(m);
            Main.logger.info(exe.getUsername() + "'s money have been set to " + m);
        }, money);
        builder.implement((exe, ctx) ->
            Main.logger.info(exe.getUsername() + " have $" + User.find(exe).money()));
    }
}
