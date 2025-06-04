package com.github.treesontop.commands;

import com.github.treesontop.commands.util.UserCMDBase;
import com.github.treesontop.commands.util.UserCMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.entity.GameMode;

@RegisterCommand(value = "gamemode", alias = {"gm"})
public class GamemodeCommand extends UserCMDBase {
    @Override
    protected void build(UserCMDBuilder builder) {
        ArgumentEnum<GameMode> GamemodeArgument = new ArgumentEnum<>("mode", GameMode.class);

        builder.implement(
                (p, ctx) -> {
                    GameMode mode = ctx.get(GamemodeArgument);
                    p.setGameMode(mode);
                    p.sendMessage("Your gamemode has been set to " + mode.name());
                },
                annotator -> annotator.annotate(0, "creative | survival | adventure | spectator"),
                GamemodeArgument
        );
    }
}
