package com.github.treesontop.events.player;

import com.github.treesontop.events.EventBase;
import com.github.treesontop.events.RegisterEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerSkinInitEvent;

@RegisterEvent
public class PlayerLoadSkin extends EventBase<PlayerSkinInitEvent> {
    @Override
    public Result execute(PlayerSkinInitEvent event) {
        Player player = event.getPlayer();

        for (int i = 0; i < 5; i++) {
            try {
                PlayerSkin skin = PlayerSkin.fromUuid(player.getUuid().toString());
                event.setSkin(skin);
                return Result.SUCCESS;
            } catch (Exception ignored) {}
        }

        return Result.SUCCESS;
    }
}
