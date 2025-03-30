package com.github.treesontop;

import com.github.treesontop.commands.util.CMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.SchedulerManager;

import java.io.InvalidObjectException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static MinecraftServer minecraftServer;
    public static InstanceManager instanceManager;

    public static Map<World, Instance> instances;

    public static void main(String[] args) {
        connectToDB();
        startUp();
    }

    /**
     * Starts up the server and registers events and commands.
     */
    public static void startUp() {
        startServer();
        SchedulerManager schedulerManager = MinecraftServer.getSchedulerManager();
        schedulerManager.buildShutdownTask(Main::shutdownTask);

        try {
            registerEvent();
            registerCommand();

            MojangAuth.init();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during startup", e);
            throw new RuntimeException(e);
        }

        minecraftServer.start("0.0.0.0", 25565);
    }

    /**
     * Connects to the database.
     */
    public static void connectToDB() {
        var url = "C:\\Users\\kevin\\IdeaProjects\\ForgeRPG\\TempSQLDataBase\\data.db";

        try (var conn = DriverManager.getConnection(url)) {
            DataBase.setupDataBase(conn);
            logger.info("Connection to SQLite has been established.");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Database connection error", e);
        } catch (InvalidObjectException e) {
            logger.log(Level.SEVERE, "Database setup error", e);
            shutdownTask();
        }
    }

    /**
     * Starts the server and sets up the world.
     */
    private static void startServer() {
        startUpProperties();
        setupWorld();
    }

    /**
     * Sets up the server properties.
     */
    private static void startUpProperties() {
        System.setProperty("minestom.chunk-view-distance", "8");
        System.setProperty("minestom.tps", "20");
    }

    /**
     * Sets up the world instance and generator.
     */
    private static void setupWorld() {
        instances = new HashMap<>();
        minecraftServer = MinecraftServer.init();
        instanceManager = MinecraftServer.getInstanceManager();

        InstanceContainer world = instanceManager.createInstanceContainer();
        world.setChunkSupplier(LightingChunk::new);
        world.setGenerator(unit -> unit.modifier().setAll((x, y, z) -> {
            Vec pos = new Vec(x, y, z);
            if (y < 0) return Block.BEDROCK;
            if (pos.withY(y - 32).lengthSquared() <= 100) {
                if (y < 31) return Block.WATER;
                return Block.AIR;
            }
            if (y == 32 && Math.random() < 0.1) return Block.SHORT_GRASS;
            if (y == 31) return Block.GRASS_BLOCK;
            if (y < 31) return Block.DIRT;
            return Block.AIR;
        }));

        instances.put(World.MAIN, world);
    }

    /**
     * Shuts down the server and saves user data.
     */
    private static void shutdownTask() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
            //TODO: save user data
            player.kick("Server shutting down");
        });

        System.exit(0);
    }

    /**
     * Registers events with the global event handler.
     *
     * @throws Exception if an error occurs during event registration
     */
    private static void registerEvent() throws Exception {
        GlobalEventHandler eventHandler = MinecraftServer.getGlobalEventHandler();

        Set<Class<?>> v = Util.getAnnotatedClass("com.github.treesontop.events", RegisterEvent.class);
        logger.info("Events to register: " + v.size());
        for (Class<?> c : v) {
            ((EventBase<?>) c.getDeclaredConstructor().newInstance()).register(eventHandler);
            logger.info(c.getSimpleName() + " registered");
        }
    }

    /**
     * Registers commands with the command manager.
     *
     * @throws ReflectiveOperationException if an error occurs during command registration
     */
    private static void registerCommand() throws ReflectiveOperationException {
        Set<Class<?>> v = Util.getAnnotatedClass("com.github.treesontop.commands", RegisterCommand.class);
        logger.info("Commands to register: " + v.size());
        for (Class<?> c : v) {
            switch (c.getDeclaredConstructor().newInstance()) {
                case CMDBase c1 -> c1.register();
                case PlayerOnlyCMDBase c2 -> c2.register();
                default -> throw new RuntimeException("Unknown type");
            }
            logger.info(c.getSimpleName() + " registered");
        }
    }
}
