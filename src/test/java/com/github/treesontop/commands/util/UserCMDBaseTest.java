package com.github.treesontop.commands.util;

import com.github.treesontop.Util;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserCMDBaseTest {
    private UserCMDBase userCMDBase;
    private CommandManager cmdManager;

    @BeforeEach
    public void setUp() {
        cmdManager = mock(CommandManager.class);
        userCMDBase = new MockCMD();
    }

    @Test
    public void testRegister() {
        userCMDBase.register(cmdManager);
        verify(cmdManager, times(1)).register(any(Command.class));
    }

    @Test
    public void testExtractBaseCommand() {
        Command command = userCMDBase.extractBaseCommand();
        assertEquals("mocked_cmd", command.getName());
    }

    @Util.DoNotScan
    @RegisterCommand(value = "mocked_cmd")
    private static class MockCMD extends UserCMDBase {
        @Override
        protected void build(UserCMDBuilder builder) {
            Argument<String> arg = ArgumentType.String("arg");
            builder.implement((sender, context) -> {}, arg);
        }
    }
}
