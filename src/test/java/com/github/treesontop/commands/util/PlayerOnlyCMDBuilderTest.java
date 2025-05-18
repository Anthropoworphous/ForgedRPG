package com.github.treesontop.commands.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PlayerOnlyCMDBuilderTest {

    private PlayerOnlyCMDBuilder playerOnlyCMDBuilder;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        playerOnlyCMDBuilder = new PlayerOnlyCMDBuilder();
        logger = mock(Logger.class);
    }

    @Test
    public void testImplement() {
        PlayerOnlyCMDBuilder.PlayerExecutor executor = mock(PlayerOnlyCMDBuilder.PlayerExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        playerOnlyCMDBuilder.implement(executor, arg);

        assertEquals(1, playerOnlyCMDBuilder.counts);
        assertEquals(executor, playerOnlyCMDBuilder.executor.get(0));
        assertEquals(arg, playerOnlyCMDBuilder.argList.get(0)[0]);
    }

    @Test
    public void testGenerateArgsDescription() {
        PlayerOnlyCMDBuilder.PlayerExecutor executor = mock(PlayerOnlyCMDBuilder.PlayerExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        playerOnlyCMDBuilder.implement(executor, annotator -> annotator.annotate(0, "Test annotation"), arg);

        List<TextComponent> descriptions = playerOnlyCMDBuilder.generateArgsDescription("testCommand", 0);

        assertEquals(2, descriptions.size());
        assertEquals(Component.text("testCommand usage:").color(NamedTextColor.GOLD), descriptions.get(0));
        assertEquals(Component.text("    <arg>: ").color(NamedTextColor.GOLD)
                .append(Component.text("Test annotation", NamedTextColor.GRAY)), descriptions.get(1));
    }

    @Test
    public void testPlayerExecutor() {
        PlayerOnlyCMDBuilder.PlayerExecutor executor = (sender, context) -> {
            if (sender instanceof Player) {
                sender.sendMessage("Command executed");
            }
        };

        CommandSender sender = mock(Player.class);
        CommandContext context = mock(CommandContext.class);

        executor.apply(sender, context);

        verify(sender, times(1)).sendMessage("Command executed");
    }
}
