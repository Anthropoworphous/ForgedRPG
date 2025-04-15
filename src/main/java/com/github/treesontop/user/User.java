package com.github.treesontop.user;

import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.data.SQLInt;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.MakeColumn;
import com.github.treesontop.database.table.Table;
import net.minestom.server.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

@GenerateTable(name = "user")
public class User {
    private static final Logger logger = Logger.getGlobal();
    private static final Map<UUID, User> users = new HashMap<>();

    private static Table table;

    public final Player player;

    @MakeColumn(name = "uuid", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
    public final UUID uuid;

    @MakeColumn(name = "money", type = SQLDataType.INT)
    private int money = 0;

    public User(Player player) {
        this.player = player;
        this.uuid = player.getUuid();
    }

    public static User load(Player player) throws SQLException {
        logger.info("Loading player " + player.getUsername() + " @" + player.getUuid());
        var uuid = player.getUuid();

        var resultSet = table.findExact(
            table.column("uuid").fill(new SQLText(SQLDataType.TINYTEXT, uuid.toString())),
            "money"
        ).execute();

        var user = resultSet.map(result -> {
            var tempUser = new User(player);
            try {
                tempUser.money = result.getInt("money");
            } catch (SQLException ignored) {}
            return tempUser;
        }).orElse(new User(player));

        users.put(uuid, user);

        return user;
    }

    public static void save(Player player) throws SQLException {
        logger.info("Saving player " + player.getUsername() + " @" + player.getUuid());
        var uuid = player.getUuid();
        var user = users.remove(uuid);

        table.insert().insert(
            table.key().fill(new SQLText(SQLDataType.TINYTEXT, uuid.toString())),
            table.column("money").fill(new SQLInt(SQLDataType.INT, user.money))
        ).execute();
    }

    public void msg(String msg) {
        player.sendMessage(msg);
    }



    public int money() { return money; }
    public void money(int money) { this.money = money; }
}
