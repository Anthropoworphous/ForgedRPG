package com.github.treesontop.commands.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class UserCMDBuilderTest {

    private UserCMDBuilder userCMDBuilder;

    @BeforeEach
    public void setUp() {
        userCMDBuilder = new UserCMDBuilder();
    }

    @Test
    public void testImplement() {
        UserCMDBuilder.UserExecutor executor = mock(UserCMDBuilder.UserExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        userCMDBuilder.implement(executor, arg);

        assertEquals(1, userCMDBuilder.counts);
        assertEquals(executor, userCMDBuilder.executor.getFirst());
        assertEquals(arg, userCMDBuilder.argList.getFirst()[0]);
    }

    @Test
    public void testGenerateArgsDescription() {
        UserCMDBuilder.UserExecutor executor = mock(UserCMDBuilder.UserExecutor.class);
        Argument<String> arg = ArgumentType.String("arg");

        userCMDBuilder.implement(executor, annotator -> annotator.annotate(0, "Test annotation"), arg);

        List<TextComponent> descriptions = userCMDBuilder.generateArgsDescription("testCommand", 0);

        assertEquals(2, descriptions.size());
        assertEquals(Component.text("testCommand usage:").color(NamedTextColor.GOLD), descriptions.get(0));
        assertEquals(Component.text("    <arg>: ").color(NamedTextColor.GOLD)
                .append(Component.text("Test annotation", NamedTextColor.GRAY)), descriptions.get(1));
    }

//    Can't test due to user class unable to be mocked
//    @Test
//    public void testPlayerExecutor() {
//    }
}
