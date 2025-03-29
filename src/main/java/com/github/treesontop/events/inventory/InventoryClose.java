package com.github.treesontop.events.inventory;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.event.inventory.InventoryCloseEvent;

@RegisterEvent
public class InventoryClose extends EventBase<InventoryCloseEvent> {
    @Override
    public Result execute(InventoryCloseEvent event) {
        // TODO: shove cursor item into warehouse when that's implemented
        return Result.SUCCESS;
    }
}
