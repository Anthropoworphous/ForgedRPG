package com.github.treesontop;

import com.github.treesontop.commands.util.CMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.instance.InstanceManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Set;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class MainTest {
    private MinecraftServer minecraftServer;
    private InstanceManager instanceManager;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        minecraftServer = mock(MinecraftServer.class);
        instanceManager = mock(InstanceManager.class);
        logger = mock(Logger.class);
    }

    @Test
    public void testStartUp() {
        Main.startUp();
        verify(minecraftServer, times(1)).start("0.0.0.0", 25565);
    }

    @Test
    public void testConnectToDB() {
        String url = "jdbc:sqlite:c:/Users/kevin/IdeaProjects/ForgeRPG/TempSQLDataBase/data.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            DataBase.setupDataBase(conn);
            assertNotNull(conn);
        } catch (SQLException | InvalidObjectException e) {
            fail("Database connection failed");
        }
    }

    @Test
    public void testRegisterEvent() throws Exception {
        Set<Class<?>> eventClasses = Set.of(EventBase.class);
        when(Util.getAnnotatedClass("com.github.treesontop.events", RegisterEvent.class)).thenReturn(eventClasses);

        Main.registerEvent();

        verify(logger, times(1)).info("Events to register: " + eventClasses.size());
        for (Class<?> eventClass : eventClasses) {
            verify(logger, times(1)).info(eventClass.getSimpleName() + " registered");
        }
    }

    @Test
    public void testRegisterCommand() throws ReflectiveOperationException {
        Set<Class<?>> commandClasses = Set.of(CMDBase.class, PlayerOnlyCMDBase.class);
        when(Util.getAnnotatedClass("com.github.treesontop.commands", RegisterCommand.class)).thenReturn(commandClasses);

        Main.registerCommand();

        verify(logger, times(1)).info("Commands to register: " + commandClasses.size());
        for (Class<?> commandClass : commandClasses) {
            verify(logger, times(1)).info(commandClass.getSimpleName() + " registered");
        }
    }
}
