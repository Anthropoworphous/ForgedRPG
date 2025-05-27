package com.github.treesontop.commands.testing;

import com.github.treesontop.Main;
import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.user.User;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;

@RegisterCommand("self_harm")
public class SelfHarmCommand extends PlayerOnlyCMDBase {
    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        var arg = new ArgumentInteger("dmg");
        builder.implement((exe, ctx) -> {
            var dmg = ctx.get(arg);
            User.find(exe).character.lossHealth(dmg);
            Main.logger.info("Removed %s health from player".formatted(dmg));
        }, arg);
    }
}
