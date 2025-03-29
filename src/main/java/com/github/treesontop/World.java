package com.github.treesontop;

import net.minestom.server.instance.Instance;

public enum World {
    MAIN;

    public Instance get() {
        return Main.instances.get(this);
    }
}
