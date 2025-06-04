package com.github.treesontop.user;

import com.github.treesontop.Main;
import com.github.treesontop.gameplay.inventory.equipment.IPlayerWearer;
import com.github.treesontop.gameplay.stats.holder.StatsHolder;
import com.github.treesontop.gameplay.stats.holder.StatsSnapshot;
import com.github.treesontop.gameplay.stats.holder.profiles.IDefaultStatsProfile;
import com.github.treesontop.gameplay.stats.impl.dmg.TrueDamage;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.art.Tenacity;
import com.github.treesontop.gameplay.stats.impl.dmg.penetration.phy.Pierce;
import com.github.treesontop.gameplay.stats.impl.hp.Health;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.TaskSchedule;

public class Character extends StatsHolder implements IDefaultStatsProfile, IPlayerWearer {
    private static final int totalHealthBarLength = 20;

    private final User user;

    public static void startUIElementUpdate() {
        MinecraftServer.getSchedulerManager().scheduleTask(() -> Main.instances.values().stream()
            .flatMap(world -> world.getPlayers().stream())
            .forEach(player -> {
                var character = User.find(player).character;
                var hp = character.snapshot().get(Health.class);
                var maxHp = character.health().range().max();
                var hpBar = Math.round(hp / maxHp * totalHealthBarLength);
                var actionBar = Component.text();
                actionBar.append(
                    Component.text("Health: [%s|%s]".formatted(hp, maxHp),
                    Style.style(NamedTextColor.RED)));
                player.sendActionBar(actionBar);
            }), TaskSchedule.nextTick(), TaskSchedule.tick(5));
    }

    public Character(User user) {
        this.user = user;
        init(new StatsSnapshot(this));
    }

    @Override
    public User user() {
        return user;
    }

    @Override
    public TrueDamage trueDamage() {
        return TrueDamage.none;
    }

    @Override
    public Pierce pierce() {
        return new Pierce(0);
    }
    @Override
    public Tenacity tenacity() {
        return new Tenacity(0);
    }
}
