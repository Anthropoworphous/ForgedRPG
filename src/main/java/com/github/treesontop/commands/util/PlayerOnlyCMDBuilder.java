package com.github.treesontop.commands.util;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerOnlyCMDBuilder extends CMDBuilder {
    private static final Logger logger = Logger.getGlobal();

    /**
     * Implements a command with the provided player executor, annotation, and arguments.
     *
     * @param exe        the player executor
     * @param annotation the annotation consumer
     * @param args       the command arguments
     * @return the PlayerOnlyCMDBuilder instance
     */
    public PlayerOnlyCMDBuilder implement(PlayerExecutor exe, Consumer<ArgumentAnnotater> annotation, Argument<?>... args) {
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
    public PlayerOnlyCMDBuilder implement(PlayerExecutor exe, Argument<?>... args) {
        this.implement((CommandExecutor) exe, args);
        return this;
    }

    @FunctionalInterface
    public interface PlayerExecutor extends CommandExecutor {
        void apply(@NotNull Player sender, @NotNull CommandContext context);

        @Override
        default void apply(@NotNull CommandSender sender, @NotNull CommandContext context) {
            if (sender instanceof Player p) {
                apply(p, context);
            } else {
                String errorMessage = "This command can only be executed by a player!";
                logger.log(Level.WARNING, errorMessage);
                sender.sendMessage(errorMessage);
            }
        }
    }
}
