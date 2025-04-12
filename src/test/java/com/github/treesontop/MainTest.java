package com.github.treesontop;

import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.builder.Command;
import net.minestom.server.event.GlobalEventHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class MainTest {
    private static MinecraftServer minecraftServer;
    private static GlobalEventHandler eventHandler;
    private static CommandManager commandManager;

    @BeforeAll
    public static void setUp() {
        minecraftServer = mock(MinecraftServer.class);
        eventHandler = mock(GlobalEventHandler.class);
        commandManager = mock(CommandManager.class);
    }

    @Test
    public void testStartUp() {
        Main.startUp(minecraftServer);
        verify(minecraftServer, times(1)).start("0.0.0.0", 25565);
    }

    @Test
    @Order(1)
    public void testConnectToDB() {
        String url = "jdbc:sqlite:c:/Users/kevin/IdeaProjects/ForgeRPG/TempSQLDataBase/test/data.db";
        try {
            Connection conn = DataBase.setupDataBase(url);
            assertNotNull(conn);
        } catch (SQLException | InvalidObjectException e) {
            fail("Database connection failed");
        }
    }

    @Test
    public void testRegisterEvent() throws Exception {
        var classes = Util.getAnnotatedClass("com.github.treesontop.events", RegisterEvent.class);
        Main.registerEvent(classes, eventHandler);
        verify(eventHandler, times(classes.size())).addListener(any());
    }

    @Test
    public void testRegisterCommand() throws ReflectiveOperationException {
        var classes = Util.getAnnotatedClass("com.github.treesontop.commands", RegisterCommand.class);

        Main.registerCommand(classes, commandManager);

        verify(commandManager, times(classes.size())).register(any(Command.class));
    }

    @Test
    @Order(2)
    public void testDataBase() {
//        try {
//            DataBase.runStatement(UserTable.tableMaker());
//            DataBase.runStatement(UserTable.insertOrReplace(Map.of(
//                "money", new SQLInt(SQLDataType.INT, 69),
//                    "uuid", new SQLText(SQLDataType.TINYTEXT, "TEST")
//                )));
//            var q = DataBase.runStatement(UserTable.querySingle(Collections.singleton("money"), Map.of("uuid", new SQLText(SQLDataType.TINYTEXT, "TEST"))));
//
//            q.ifPresent(result -> {
//                try {
//                    logger.info(String.valueOf(result.getInt("money")));
//                } catch (SQLException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//
//            DataBase.closeDataBase();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
