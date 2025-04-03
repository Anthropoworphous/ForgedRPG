package com.github.treesontop.commands.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import java.util.logging.Logger;

public abstract class CMDBase {
    private static final CommandManager cmdManager = MinecraftServer.getCommandManager();
    private static final Logger logger = Logger.getLogger(CMDBase.class.getName());

    /**
     * Registers the command with the command manager.
     */
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

        // Log the command syntaxes tree
        logger.info(cmd.getSyntaxesTree().toString());

        cmdManager.register(cmd);
    }

    /**
     * Extracts the base command from the class annotation.
     *
     * @return the base command
     * @throws RuntimeException if the class is not annotated with @RegisterCommand
     */
    protected Command extractBaseCommand() {
        RegisterCommand ann = getClass().getAnnotation(RegisterCommand.class);
        if (ann == null) {
            String errorMessage = "Class " + getClass().getName() + " not annotated";
            logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return new Command(ann.name(), ann.alias());
    }

    /**
     * Builds the command using the provided CMDBuilder.
     *
     * @param builder the CMDBuilder to use for building the command
     */
    protected abstract void build(CMDBuilder builder);
}
