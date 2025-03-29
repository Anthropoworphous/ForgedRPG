package com.github.treesontop.user;

import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.setup.processor.MakeColumn;
import net.minestom.server.entity.Player;

import java.util.UUID;

public class User {
    private Player _player;

    @MakeColumn(datatype = SQLDataType.TINYTEXT, config = MakeColumn.Config.KEY)
    private UUID _uuid;

    public User(Player player) {
        _player = player;
        _uuid = player.getUuid();
    }
}
