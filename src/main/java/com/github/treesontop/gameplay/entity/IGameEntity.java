package com.github.treesontop.gameplay.entity;

import com.github.treesontop.Util;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.instance.Instance;
import net.minestom.server.tag.Tag;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public interface IGameEntity {
    static Map<String, Map<UUID, IGameEntity>> entityPool = new HashMap<>();
    static Map<String, EntityFactory> factories = new HashMap<>();
    static final Tag<String> gameEntityTag = Tag.String("game_entity").defaultValue("INVALID");

    static void registerAll() {
        var namespace = "com.github.treesontop.gameplay.entity.impl";
        for (Class<?> clazz : Util.getAnnotatedClass(namespace, RegisterEntity.class)) {
            entityPool.put(clazz.getAnnotation(RegisterEntity.class).value(), new HashMap<>());
            try {
                var e = clazz.getConstructor().newInstance();
                if (!(e instanceof IGameEntity entity)) throw new RuntimeException("Invalid type");
                factories.put(entity.tag(), entity.factory());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    static EntityFactory factory(String tag) {
        var factory = factories.get(tag);
        if (factory == null) throw new RuntimeException("Invalid entity tag");
        return factory;
    }

    static IGameEntity get(Entity entity) {
        return IGameEntity.entityPool
            .get(entity.getTag(IGameEntity.gameEntityTag))
            .get(entity.getUuid());
    }
    static IGameEntity remove(Entity entity) {
        return IGameEntity.entityPool
            .get(entity.getTag(IGameEntity.gameEntityTag))
            .remove(entity.getUuid());
    }

    @CanIgnoreReturnValue
    default Entity spawn(Instance instance, Pos pos) {
        var entity = modify(new Entity(type()));
        entity.setInstance(instance, pos);
        entityPool.get(tag()).put(entity.getUuid(), this);
        return entity;
    }

    String tag();

    EntityType type();

    default Entity modify(Entity original) {
        original.setTag(gameEntityTag, tag());
        return original;
    }

    EntityFactory factory();

    abstract class EntityFactory {
        public abstract IGameEntity create();
    }
}
