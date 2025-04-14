package com.github.treesontop.user;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.MakeColumn;
import net.minestom.server.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@GenerateTable(name = "user")
public class User {
    private static final Logger logger = Logger.getLogger(User.class.getName());
    private static Map<UUID, User> userMap = new HashMap<>();

    public final Player player;

    @MakeColumn(name = "uuid", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
    public final UUID uuid;
    @MakeColumn(name = "money", type = SQLDataType.INT)
    private int money;

    public static void load(Player player) throws SQLException {
        var user = new User(player);
        var uuid = player.getUuid();
//        var result = DataBase.runStatement(UserTable.querySingle(
//            Set.of("uuid", "money"),
//            Map.of("uuid", new SQLText(SQLDataType.TINYTEXT, uuid.toString()))));
//
//        if (result.isEmpty()) {
//            logger.info("Player %s have no data!".formatted(player.getName()));
//            user.money = 0;
//        } else {
//            var data = result.get();
//            user.money = data.getInt("money");
//        }

    }

    public static void save(Player player) throws SQLException {
        var uuid = player.getUuid();
        var user = userMap.get(uuid);
//        DataBase.runStatement(UserTable.insertOrReplace(Map.of(
//            "uuid", new SQLText(SQLDataType.TINYTEXT, uuid.toString()),
//            "money", new SQLInt(SQLDataType.INT, user.money)
//        )));
    }

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUuid();
    }
}
