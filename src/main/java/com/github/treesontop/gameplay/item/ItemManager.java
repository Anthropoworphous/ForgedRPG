package com.github.treesontop.gameplay.item;

import com.github.treesontop.Main;
import com.github.treesontop.Util;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ItemManager {
    private static final Map<String, IGameItem> itemRegistry = new HashMap<>();

    public static void registerAllItems() {
        var classes = Util.getAnnotatedClass("com.github.treesontop.gameplay.item", Register.class);
        Main.logger.info("Item to register: " + classes.size());
        for (var c : classes) {
            try {
                if (c.isInterface()) continue;
                var item = c.getConstructor().newInstance();
                if (!(item instanceof IGameItem gameItem)) continue;

                var id = c.getAnnotation(Register.class).value();
                gameItem.init();
                itemRegistry.put(id, gameItem);
                Main.logger.info("Item %s registered".formatted(id));
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
