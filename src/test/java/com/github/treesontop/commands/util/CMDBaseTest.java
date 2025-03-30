package com.github.treesontop.commands.util;

import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandExecutor;
import net.minestom.server.command.builder.arguments.Argument;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CMDBaseTest {

    private CMDBase cmdBase;
    private CommandManager cmdManager;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        cmdManager = mock(CommandManager.class);
        logger = mock(Logger.class);
        cmdBase = new CMDBase() {
            @Override
            protected void build(CMDBuilder builder) {
                Argument<String> arg = ArgumentType.String("arg");
                builder.implement((sender, context) -> {
                    if (sender instanceof Player) {
                        sender.sendMessage("Command executed");
                    }
                }, arg);
            }
        };
    }

    @Test
    public void testRegister() {
        cmdBase.register();
        verify(cmdManager, times(1)).register(any(Command.class));
    }

    @Test
    public void testExtractBaseCommand() {
        Command command = cmdBase.extractBaseCommand();
        assertEquals("test", command.getName());
    }
}
