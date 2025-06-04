package com.github.treesontop.commands;

import com.github.treesontop.commands.util.UserCMDBase;
import com.github.treesontop.commands.util.UserCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.gameplay.entity.IGameEntity;
import net.minestom.server.command.builder.arguments.ArgumentString;

@RegisterCommand("spawn")
public class SpawnGameEntity extends UserCMDBase {
    @Override
    protected void build(UserCMDBuilder builder) {
        var tagArg = new ArgumentString("tag");

        builder.implement((exe, ctx) ->
            IGameEntity.factories.get(ctx.get(tagArg))
                .create()
                .spawn(exe.getInstance(), exe.getPosition()),
            annotator -> annotator.annotate(0, "Tag of entity to spawn"),
            tagArg
        );
    }
}
