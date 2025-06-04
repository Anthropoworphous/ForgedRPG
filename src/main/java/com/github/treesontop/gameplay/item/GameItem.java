package com.github.treesontop.gameplay.item;

import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

public abstract class GameItem {
    public static final Tag<String> nameTag = Tag.String("name");

    public final ItemStack raw;

    public GameItem(ItemStack raw) {
        this.raw = raw;
    }

    public String name() {
        var name = raw.getTag(nameTag);
        return name == null ? raw.material().name() : name;
    }

    public boolean isLegal() {
        return raw.hasTag(nameTag);
    }

    public void update() {}
}
