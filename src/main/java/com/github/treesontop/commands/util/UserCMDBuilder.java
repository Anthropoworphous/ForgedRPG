package com.github.treesontop.commands.util;

import com.github.treesontop.Main;
import com.github.treesontop.user.User;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Level;


public class UserCMDBuilder extends CMDBuilder {


    /**
     * Implements a command with the provided player executor, annotation, and arguments.
     *
     * @param exe        the player executor
     * @param annotation the annotation consumer
     * @param args       the command arguments
     * @return the PlayerOnlyCMDBuilder instance
     */
    public UserCMDBuilder implement(UserExecutor exe, Consumer<ArgumentAnnotator> annotation, Argument<?>... args) {
        this.implement((CommandExecutor) exe, annotation, args);
        return this;
    }

    /**
     * Implements a command with the provided player executor and arguments.
     *
     * @param exe  the player executor
     * @param args the command arguments
     * @return the PlayerOnlyCMDBuilder instance
     */
    public UserCMDBuilder implement(UserExecutor exe, Argument<?>... args) {
        this.implement((CommandExecutor) exe, args);
        return this;
    }

    @FunctionalInterface
    public interface UserExecutor extends CommandExecutor {
        void apply(@NotNull User sender, @NotNull CommandContext context);

        @Override
        default void apply(@NotNull CommandSender sender, @NotNull CommandContext context) {
            if (sender instanceof User user) {
                apply(user, context);
            } else {
                String errorMessage = "This command can only be executed by a player!";
                Main.logger.log(Level.WARNING, errorMessage);
                sender.sendMessage(errorMessage);
            }
        }
    }
}
