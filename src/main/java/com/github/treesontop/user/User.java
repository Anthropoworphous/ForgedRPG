package com.github.treesontop.user;

import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.setup.processor.MakeColumn;
import net.minestom.server.entity.Player;

import java.util.UUID;

public class User {
    public final Player player;

    @MakeColumn(datatype = SQLDataType.TINYTEXT, config = MakeColumn.Config.KEY)
    public final UUID uuid;
    @MakeColumn(datatype = SQLDataType.INT, config = MakeColumn.Config.DEFAULT)
    private int money;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUuid();
    }
}
