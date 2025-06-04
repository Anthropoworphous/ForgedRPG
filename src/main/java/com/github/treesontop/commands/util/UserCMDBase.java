package com.github.treesontop.commands.util;

import com.github.treesontop.Main;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;



public abstract class UserCMDBase {


    /**
     * Registers the command with the command manager.
     */
    public void register(CommandManager cmdManager) {
        Command cmd = extractBaseCommand();
        UserCMDBuilder builder = new UserCMDBuilder();
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
            Main.logger.severe(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        return new Command(ann.value(), ann.alias());
    }

    /**
     * Builds the command using the provided PlayerOnlyCMDBuilder.
     *
     * @param builder the PlayerOnlyCMDBuilder to use for building the command
     */
    protected abstract void build(UserCMDBuilder builder);
}
