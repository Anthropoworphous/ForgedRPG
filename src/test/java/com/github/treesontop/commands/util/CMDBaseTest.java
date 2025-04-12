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

public class CMDBaseTest {
    private CMDBase cmdBase;
    private CommandManager cmdManager;

    @BeforeEach
    public void setUp() {
        cmdManager = mock(CommandManager.class);
        cmdBase = new MockCMD();
    }

    @Test
    public void testRegister() {
        cmdBase.register(cmdManager);
        verify(cmdManager, times(1)).register(any(Command.class));
    }

    @Test
    public void testExtractBaseCommand() {
        Command command = cmdBase.extractBaseCommand();
        assertEquals("mocked_cmd", command.getName());
    }

    @Util.DoNotScan
    @RegisterCommand(name = "mocked_cmd")
    private static class MockCMD extends CMDBase {
        @Override
        protected void build(CMDBuilder builder) {
            Argument<String> arg = ArgumentType.String("arg");
            builder.implement((sender, context) -> {}, arg);
        }
    }
}
