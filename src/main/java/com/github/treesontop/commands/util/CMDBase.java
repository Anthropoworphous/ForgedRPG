package com.github.treesontop.commands.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;

public abstract class CMDBase {
    private static final CommandManager cmdManager = MinecraftServer.getCommandManager();

    public void register() {
        Command cmd = extractBaseCommand();
        CMDBuilder builder = new CMDBuilder();
        build(builder);

        for (int i = 0; i < builder.counts; i++) {
            cmd.addSyntax(builder.executor.get(i), builder.argList.get(i));

            int finalI = i;
            cmd.setDefaultExecutor((sender, ignore) -> builder.generateArgsDescription(cmd.getName(), finalI)
                    .forEach(sender::sendMessage));
        }

        //noinspection UnstableApiUsage
        System.out.println(cmd.getSyntaxesTree());

        cmdManager.register(cmd);
    }

    protected Command extractBaseCommand() {
        RegisterCommand ann = getClass().getAnnotation(RegisterCommand.class);
        if (ann == null) {
            throw new RuntimeException("Class " + getClass().getName() + " not annotated");
        }
        return new Command(ann.name(), ann.alias());
    }

    protected abstract void build(CMDBuilder builder);
}
