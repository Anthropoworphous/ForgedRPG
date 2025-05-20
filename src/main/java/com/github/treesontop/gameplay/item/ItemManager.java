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
                if (c.isInterface()) continue;
                var item = c.getConstructor().newInstance();
                if (!(item instanceof IGameItem gameItem)) continue;

                var id = new StringBuilder();
                var cSup = c;
                do {
                    Register annotation = cSup.getAnnotation(Register.class);
                    if (annotation != null) {
                        String value = annotation.value();
                        if (value != null) id.insert(0, value + "/");
                    }
                    cSup = cSup.getSuperclass();
                } while (cSup != null);
                gameItem.init();
                itemRegistry.put(id.toString(), gameItem);
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
        String value();
    }
}
