package com.github.treesontop.commands;

import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.gameplay.entity.IGameEntity;
import net.minestom.server.command.builder.arguments.ArgumentString;

@RegisterCommand("spawn")
public class SpawnGameEntity extends PlayerOnlyCMDBase {
    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        var tagArg = new ArgumentString("tag");

        builder.implement((exe, ctx) -> {
            IGameEntity.factories.get(ctx.get(tagArg)).create().spawn(exe.getInstance(), exe.getPosition());
        }, annotator -> annotator.annotate(0, "Tag of entity to spawn"), tagArg);
    }
}
