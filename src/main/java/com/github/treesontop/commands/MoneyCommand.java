package com.github.treesontop.commands;

import com.github.treesontop.Main;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.commands.util.UserCMDBase;
import com.github.treesontop.commands.util.UserCMDBuilder;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;



@RegisterCommand("money")
public class MoneyCommand extends UserCMDBase {
    @Override
    protected void build(UserCMDBuilder builder) {
        var money = new ArgumentInteger("money");

        builder.implement((exe, ctx) -> {
            var m = ctx.get(money);
            exe.money(m);
            Main.logger.info(exe.getUsername() + "'s money have been set to " + m);
        }, money);
        builder.implement((exe, ctx) ->
            Main.logger.info(exe.getUsername() + " have $" + exe.money()));
    }
}
