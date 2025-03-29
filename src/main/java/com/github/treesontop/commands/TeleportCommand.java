package com.github.treesontop.commands;

import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.ArgumentEntity;
import net.minestom.server.command.builder.arguments.relative.ArgumentRelativeVec3;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;

import java.util.List;

@RegisterCommand(name = "tp", alias = {"teleport"})
public class TeleportCommand extends PlayerOnlyCMDBase {
    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        ArgumentRelativeVec3 posArg = ArgumentType.RelativeVec3("pos");
        ArgumentEntity targetArg = ArgumentType.Entity("target");
        ArgumentEntity otherTargetArg = ArgumentType.Entity("otherTarget").singleEntity(true);

        builder.implement((p, ctx) -> p.teleport(ctx.get(posArg).from(p.getPosition()).asPosition()), posArg)
            .implement(((p, ctx) -> {
                Entity v = ctx.get(otherTargetArg).findFirstEntity(p);
                if (v != null) p.teleport(v.getPosition());
            }), otherTargetArg)
            .implement(((p, ctx) -> {
                List<Entity> list = ctx.get(targetArg).find(p);
                Pos pos = ctx.get(posArg).from(p.getPosition()).asPosition();
                list.forEach(v -> v.teleport(pos));
            }), targetArg, posArg)
            .implement((p, ctx) -> {
                List<Entity> list = ctx.get(targetArg).find(null, null);
                Entity v = ctx.get(otherTargetArg).findFirstEntity(p);
                if (v != null) list.forEach(e -> e.teleport(v.getPosition()));
            }, targetArg, otherTargetArg);
    }
}