package com.github.treesontop;

import com.github.treesontop.commands.util.CMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.database.DataBase;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.TableGenerator;
import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import com.github.treesontop.gameplay.entity.IGameEntity;
import com.github.treesontop.gameplay.item.ItemManager;
import com.github.treesontop.user.User;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandManager;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.block.Block;
import net.minestom.server.timer.SchedulerManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final Map<String, String> env = System.getenv();
    public static final Logger logger = Logger.getGlobal();

    public static MinecraftServer minecraftServer;
    public static InstanceManager instanceManager;

    public static Map<World, Instance> instances;

    public static void main(String[] args) {
        Console.link();
        DataBase.connectToDB();
        TableGenerator.generate(Util.getAnnotatedClass("com.github.treesontop", GenerateTable.class));
        ItemManager.registerAllItems();

        startUp(minecraftServer = MinecraftServer.init());
    }

    /**
     * Starts up the server and registers events and commands.
     */
    public static void startUp(MinecraftServer minecraftServer) {
        startServer();
        SchedulerManager schedulerManager = MinecraftServer.getSchedulerManager();
        schedulerManager.buildShutdownTask(Main::shutdown);

        try {
            IGameEntity.registerAll();

            registerEvent(Util.getAnnotatedClass("com.github.treesontop.events", RegisterEvent.class),
                MinecraftServer.getGlobalEventHandler());
            registerCommand(Util.getAnnotatedClass("com.github.treesontop.commands", RegisterCommand.class),
                MinecraftServer.getCommandManager());

            MojangAuth.init();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during startup", e);
            throw new RuntimeException(e);
        }

        minecraftServer.start("0.0.0.0", 25565);

        logger.info("Server started!");
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
    public static void shutdown() {
        MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
            try {
                User.save(player);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            player.kick("Server shutting down");
        });

        try {
            DataBase.closeDataBase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            logger.info("No database were connected");
        }

        System.exit(0);
    }

    /**
     * Registers events with the global event handler.
     *
     * @throws Exception if an error occurs during event registration
     */
    static void registerEvent(Set<Class<?>> classes, GlobalEventHandler eventHandler) throws Exception {
        logger.info("Events to register: " + classes.size());
        for (Class<?> c : classes) {
            ((EventBase<?>) c.getDeclaredConstructor().newInstance()).register(eventHandler);
            logger.info(c.getSimpleName() + " registered");
        }
    }

    /**
     * Registers commands with the command manager.
     *
     * @throws ReflectiveOperationException if an error occurs during command registration
     */
    static void registerCommand(Set<Class<?>> classes, CommandManager commandManager) throws ReflectiveOperationException {
        logger.info("Commands to register: " + classes.size());
        for (Class<?> c : classes) {
            switch (c.getDeclaredConstructor().newInstance()) {
                case CMDBase c1 -> c1.register(commandManager);
                case PlayerOnlyCMDBase c2 -> c2.register(commandManager);
                default -> throw new RuntimeException("Unknown type");
            }
            logger.info(c.getSimpleName() + " registered");
        }
    }
}
