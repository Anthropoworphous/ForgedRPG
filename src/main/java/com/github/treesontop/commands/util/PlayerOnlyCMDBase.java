package com.github.treesontop.commands.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;

public abstract class PlayerOnlyCMDBase {
    private static final CommandManager cmdManager = MinecraftServer.getCommandManager();

    public void register() {
        Command cmd = extractBaseCommand();
        PlayerOnlyCMDBuilder builder = new PlayerOnlyCMDBuilder();
        build(builder);

        for (int i = 0; i < builder.counts; i++) {
            Argument<?>[] argList = builder.argList.get(i);

            cmd.addSyntax(builder.executor.get(i), argList);

            int finalI = i;
            cmd.setDefaultExecutor((sender, ignore) -> builder.generateArgsDescription(cmd.getName(), finalI)
                    .forEach(sender::sendMessage));
        }

        cmdManager.register(cmd);
    }

    protected Command extractBaseCommand() {
        RegisterCommand ann = getClass().getAnnotation(RegisterCommand.class);
        if (ann == null) {
            throw new RuntimeException("Class " + getClass().getName() + " not annotated");
        }
        return new Command(ann.name(), ann.alias());
    }

    protected abstract void build(PlayerOnlyCMDBuilder builder);
}
