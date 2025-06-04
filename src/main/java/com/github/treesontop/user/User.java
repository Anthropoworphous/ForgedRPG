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
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.UUID;


@GenerateTable("user")
public class User extends Player {
    private static Table table;

    public final Character character = new Character(this);

    @MakeColumn(name = "uuid", type = SQLDataType.TINYTEXT, config = Column.Config.KEY)
    public final UUID uuid = getUuid();

    @MakeColumn(name = "gamemode", type = SQLDataType.BYTE)
    private byte gamemode = 0;

    @MakeColumn(name = "money", type = SQLDataType.INT)
    private int money = 0;

    public User(@NotNull PlayerConnection playerConnection, @NotNull GameProfile gameProfile) {
        super(playerConnection, gameProfile);
    }

    public void load() throws SQLException {
        Main.logger.info("Loading player " + getUsername() + " @" + getUuid());

        var resultSet = table.findExact(
            table.column("uuid").fill(new SQLText(SQLDataType.TINYTEXT, uuid.toString())),
            "money", "gamemode"
        ).execute();

        resultSet.ifPresentOrElse(result -> {
            try {
                money = result.getInt("money");
                gamemode = result.getByte("gamemode");
            } catch (SQLException ignored) {}
        }, () -> {
            // new player logic here
        });

        setGameMode(GameMode.values()[gamemode]);
    }

    public static void save(Player player) throws SQLException {
        var user = (User) player;
        Main.logger.info("Saving player " + player.getUsername() + " @" + player.getUuid());

        var mode = player.getGameMode().ordinal();

        var row = new Row(table, table.key().fill(new SQLText(SQLDataType.TINYTEXT, player.getUuid().toString())),
            table.column("money").fill(new SQLInt(SQLDataType.INT, user.money)),
            table.column("gamemode").fill(new SQLInt(SQLDataType.BYTE, mode))
        );

        Main.logger.info("Data: [%s]".formatted(row.toString()));

        table.insert().insert(row).execute();
    }



    public int money() { return money; }
    public void money(int money) { this.money = money; }
}
