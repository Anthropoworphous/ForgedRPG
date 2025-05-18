package com.github.treesontop.commands.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CMDBuilderTest {

    private CMDBuilder cmdBuilder;

    @BeforeEach
    public void setUp() {
        cmdBuilder = new CMDBuilder();
    }

    @Test
    public void testImplement() {
        CommandExecutor executor = mock(CommandExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        cmdBuilder.implement(executor, arg);

        assertEquals(1, cmdBuilder.counts);
        assertEquals(executor, cmdBuilder.executor.get(0));
        assertEquals(arg, cmdBuilder.argList.get(0)[0]);
    }

    @Test
    public void testGenerateArgsDescription() {
        CommandExecutor executor = mock(CommandExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        cmdBuilder.implement(executor, annotator -> annotator.annotate(0, "Test annotation"), arg);

        List<TextComponent> descriptions = cmdBuilder.generateArgsDescription("testCommand", 0);

        assertEquals(2, descriptions.size());
        assertEquals(Component.text("testCommand usage:").color(NamedTextColor.GOLD), descriptions.get(0));
        assertEquals(Component.text("    <arg>: ").color(NamedTextColor.GOLD)
                .append(Component.text("Test annotation", NamedTextColor.GRAY)), descriptions.get(1));
    }

    @Test
    public void testArgumentAnnotator() {
        CMDBuilder.ArgumentAnnotator annotator = new CMDBuilder.ArgumentAnnotator(1);
        Supplier<String> annotationSupplier = () -> "Test annotation";

        annotator.annotate(0, annotationSupplier);

        assertEquals(annotationSupplier, annotator.annotations.get(0));
    }
}
