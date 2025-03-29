package com.github.treesontop.commands;

import com.github.treesontop.commands.util.PlayerOnlyCMDBase;
import com.github.treesontop.commands.util.PlayerOnlyCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.entity.GameMode;

@RegisterCommand(name = "gamemode", alias = {"gm"})
public class GamemodeCommand extends PlayerOnlyCMDBase {
    @Override
    protected void build(PlayerOnlyCMDBuilder builder) {
        ArgumentEnum<GameMode> GamemodeArgument = new ArgumentEnum<>("mode", GameMode.class);

        builder.implement(
                (p, ctx) -> {
                    GameMode mode = ctx.get(GamemodeArgument);
                    p.setGameMode(mode);
                    p.sendMessage("Your gamemode has been set to " + mode.name());
                },
                annotater -> annotater.annotate(0, "creative | survival | adventure | spectator"),
                GamemodeArgument
        );
    }
}
