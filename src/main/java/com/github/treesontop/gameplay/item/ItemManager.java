package com.github.treesontop.gameplay.item;

import com.github.treesontop.Util;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemManager {
    private static final Map<String, IGameItem> itemRegistry = new HashMap<>();

    static {
        var classes = Util.getAnnotatedClass("com.github.treesontop.gameplay.item", Register.class);
        for (var c : classes) {
            try {
                var item = c.getConstructor().newInstance();
                if (!(item instanceof IGameItem)) continue;
                itemRegistry.put(c.getAnnotation(Register.class).id(), (IGameItem) item);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Optional<IGameItem> getItem(ItemStack raw) {
        var id = raw.getTag(Tag.String("item_id"));
        return getItem(id);
    }
    public static Optional<IGameItem> getItem(String id) {
        return Optional.ofNullable(itemRegistry.get(id));
    }



    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Register {
        String id();
    }
}
