package com.github.treesontop.user;

import com.github.treesontop.Main;
import com.github.treesontop.database.Column;
import com.github.treesontop.database.SQLDataType;
import com.github.treesontop.database.data.SQLInt;
import com.github.treesontop.database.data.SQLText;
import com.github.treesontop.database.generator.GenerateTable;
import com.github.treesontop.database.generator.MakeColumn;
import com.github.treesontop.database.table.Row;
import com.github.treesontop.database.table.Table;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@GenerateTable("user")
public class User {

    private static final Map<UUID, User> users = new HashMap<>();

    private static Table table;

    public final Player player;
    public final Character character;

    @MakeColumn(name = "uuid", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
    public final UUID uuid;

    @MakeColumn(name = "gamemode", type = SQLDataType.BYTE)
    private byte gamemode = 0;

    @MakeColumn(name = "money", type = SQLDataType.INT)
    private int money = 0;

    private User(Player player) {
        this.player = player;
        this.uuid = player.getUuid();
        character = new Character(this);
    }

    public static User find(Player player) {
        return users.get(player.getUuid());
    }
    public static User find(UUID uuid) {
        return users.get(uuid);
    }

    public static User load(Player player) throws SQLException {
        Main.logger.info("Loading player " + player.getUsername() + " @" + player.getUuid());
        var uuid = player.getUuid();

        var resultSet = table.findExact(
            table.column("uuid").fill(new SQLText(SQLDataType.TINYTEXT, uuid.toString())),
            "money", "gamemode"
        ).execute();

        var user = resultSet.map(result -> {
            var tempUser = new User(player);
            try {
                tempUser.money = result.getInt("money");
                tempUser.gamemode = result.getByte("gamemode");
            } catch (SQLException ignored) {}
            return tempUser;
        }).orElseGet(() -> new User(player));

        users.put(uuid, user);

        switch (user.gamemode) {
            case 0 -> player.setGameMode(GameMode.SURVIVAL);
            case 1 -> player.setGameMode(GameMode.CREATIVE);
            case 2 -> player.setGameMode(GameMode.SPECTATOR);
            case 3 -> player.setGameMode(GameMode.ADVENTURE);
        }

        return user;
    }
    public static void save(Player player) throws SQLException {
        var uuid = player.getUuid();
        var user = users.remove(uuid);
        Main.logger.info("Saving player " + player.getUsername() + " @" + uuid);

        var mode = switch (player.getGameMode()) {
            case GameMode.SURVIVAL -> 0;
            case GameMode.CREATIVE -> 1;
            case GameMode.SPECTATOR -> 2;
            case GameMode.ADVENTURE -> 3;
        };

        var row = new Row(table, table.key().fill(new SQLText(SQLDataType.TINYTEXT, uuid.toString())),
            table.column("money").fill(new SQLInt(SQLDataType.INT, user.money)),
            table.column("gamemode").fill(new SQLInt(SQLDataType.BYTE, mode))
        );

        Main.logger.info("Data: [%s]".formatted(row.toString()));

        table.insert().insert(row).execute();
    }

    public void msg(String msg) {
        player.sendMessage(msg);
    }



    public int money() { return money; }
    public void money(int money) { this.money = money; }
}
