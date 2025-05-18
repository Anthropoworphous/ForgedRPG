package com.github.treesontop.commands.testing;

import com.github.treesontop.commands.util.CMDBase;
import com.github.treesontop.commands.util.CMDBuilder;
import com.github.treesontop.commands.util.RegisterCommand;
import com.github.treesontop.database.DataBase;
import net.minestom.server.command.builder.arguments.ArgumentEnum;
import net.minestom.server.command.builder.arguments.ArgumentString;

import java.sql.ResultSet;
import java.sql.SQLException;

@RegisterCommand(value = "database", alias = {"db"})
public class DataBaseTestCommand extends CMDBase {
    private static ResultSet lastQuery = null;

    @Override
    protected void build(CMDBuilder builder) {
        var sql = new ArgumentString("sql");
        var mode = new ArgumentEnum<Mode>("mode", Mode.class);

        builder.implement((exe, ctx) -> {
            switch (ctx.get(mode)) {
                case RUN -> {
                    try {
                        //noinspection OptionalGetWithoutIsPresent
                        lastQuery = DataBase.runStatement(ctx.get(sql)).get();
                    } catch (SQLException e) {
                        exe.sendMessage(e.getMessage());
                    }
                }
                case GET -> {
                    if (lastQuery != null) {
                        try {
                            exe.sendMessage(lastQuery.getString(ctx.get(sql)));
                        } catch (SQLException e) {
                            exe.sendMessage(e.getMessage());
                        }
                    }
                }
                case null, default -> {}
            }
        }, mode, sql);
    }

    private enum Mode {
        RUN,
        GET
    }
}
