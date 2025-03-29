package com.github.treesontop.commands.util;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PlayerOnlyCMDBuilder extends CMDBuilder {
    public PlayerOnlyCMDBuilder implement(PlayerExecutor exe, Consumer<ArgumentAnnotater> annotation, Argument<?>... args) {
        this.implement((CommandExecutor) exe, annotation, args);
        return this;
    }
    public PlayerOnlyCMDBuilder implement(PlayerExecutor exe, Argument<?>... args) {
        this.implement((CommandExecutor) exe, args);
        return this;
    }



    @FunctionalInterface
    public interface PlayerExecutor extends CommandExecutor {
        void apply(@NotNull Player sender, @NotNull CommandContext context);

        @Override
        default void apply(@NotNull CommandSender sender, @NotNull CommandContext context) {
            if (sender instanceof Player p) apply(p, context);
            else sender.sendMessage("This command can only be execute by player!");
        }
    }
}
