package com.github.treesontop.commands.testing;

import com.github.treesontop.Main;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.commands.util.UserCMDBase;
import com.github.treesontop.commands.util.UserCMDBuilder;
import net.minestom.server.command.builder.arguments.number.ArgumentInteger;

@RegisterCommand("self_harm")
public class SelfHarmCommand extends UserCMDBase {
    @Override
    protected void build(UserCMDBuilder builder) {
        var arg = new ArgumentInteger("dmg");
        builder.implement((exe, ctx) -> {
            var dmg = ctx.get(arg);
            exe.character.lossHealth(dmg);
            Main.logger.info("Removed %s health from player".formatted(dmg));
        }, arg);
    }
}
