package com.github.treesontop.gameplay.item;

import net.minestom.server.item.ItemStack;

public interface IGameItem {
    String name();

    boolean isDefault();

    IGameItem fromItem(ItemStack item);
    ItemStack toItem();
}
