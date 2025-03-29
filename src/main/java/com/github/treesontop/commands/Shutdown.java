package com.github.treesontop.commands;

import com.github.treesontop.commands.util.CMDBase;
import com.github.treesontop.commands.util.CMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import net.minestom.server.MinecraftServer;

@RegisterCommand(name = "shutdown")
public class Shutdown extends CMDBase {
    @Override
    protected void build(CMDBuilder builder) {
        builder.implement((exe, ctx) -> MinecraftServer.stopCleanly());
    }
}
