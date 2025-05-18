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

@RegisterCommand(value = "tp", alias = {"teleport"})
public class TeleportCommand extends PlayerOnlyCMDBase {
    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        ArgumentRelativeVec3 posArg = ArgumentType.RelativeVec3("pos");
        ArgumentEntity targetArg = ArgumentType.Entity("target");
        ArgumentEntity otherTargetArg = ArgumentType.Entity("otherTarget").singleEntity(true);

        builder.implement((player, context) -> player.teleport(context.get(posArg).from(player.getPosition()).asPosition()), posArg)
                .implement(((player, context) -> {
                    Entity otherTargetEntity = context.get(otherTargetArg).findFirstEntity(player);
                    if (otherTargetEntity != null) {
                        player.teleport(otherTargetEntity.getPosition());
                    }
                }), otherTargetArg)
                .implement(((player, context) -> {
                    List<Entity> targetEntities = context.get(targetArg).find(player);
                    Pos position = context.get(posArg).from(player.getPosition()).asPosition();
                    targetEntities.forEach(entity -> entity.teleport(position));
                }), targetArg, posArg)
                .implement((player, context) -> {
                    List<Entity> targetEntities = context.get(targetArg).find(null, null);
                    Entity otherTargetEntity = context.get(otherTargetArg).findFirstEntity(player);
                    if (otherTargetEntity != null) {
                        targetEntities.forEach(entity -> entity.teleport(otherTargetEntity.getPosition()));
                    }
                }, targetArg, otherTargetArg);
    }
}
