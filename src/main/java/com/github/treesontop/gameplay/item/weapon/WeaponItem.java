package com.github.treesontop.gameplay.item.weapon;

import com.github.treesontop.gameplay.item.GameItem;
import com.github.treesontop.gameplay.stats.component.StatsRange;
import com.github.treesontop.gameplay.stats.impl.dmg.IAttack;
import com.github.treesontop.gameplay.stats.impl.dmg.art.Art;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Blunt;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Puncture;
import com.github.treesontop.gameplay.stats.impl.dmg.phy.Slash;
import net.minestom.server.item.ItemStack;
import net.minestom.server.tag.Tag;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class WeaponItem extends GameItem {
    private static final Tag<Boolean> weapon = Tag.Boolean("IsWeapon").defaultValue(false);
    private static final Tag<StatsRange> slash = Tag.Structure("SlashDamage", StatsRange.class).defaultValue(StatsRange.none);
    private static final Tag<StatsRange> blunt = Tag.Structure("BluntDamage", StatsRange.class).defaultValue(StatsRange.none);
    private static final Tag<StatsRange> puncture = Tag.Structure("PunctureDamage", StatsRange.class).defaultValue(StatsRange.none);
    private static final Tag<StatsRange> art = Tag.Structure("ArtDamage", StatsRange.class).defaultValue(StatsRange.none);

    public WeaponItem(ItemStack raw) {
        super(raw);
        updateDamage();
    }

    private final Map<String, IAttack> damages = new HashMap<>();

    public boolean isWeapon() {
        return raw.getTag(weapon);
    }

    @SuppressWarnings("unchecked")
    public <T extends IAttack> T getAttack(Class<T> type) {
        var atk = damages.get(type.getName());
        assert atk.getClass() == type;
        return (T) atk;
    }

    @Override
    public void update() {
        super.update();
        updateDamage();
    }

    private void updateDamage() {
        damages.clear();
        damages.put(Slash.class.getName(), new Slash(raw.getTag(slash)));
        damages.put(Blunt.class.getName(), new Blunt(raw.getTag(blunt)));
        damages.put(Puncture.class.getName(), new Puncture(raw.getTag(puncture)));
        damages.put(Art.class.getName(), new Art(raw.getTag(art)));
    }

    public static class Builder {
        private ItemStack raw;

        public Builder(ItemStack item) {
            this.raw = item.withTag(weapon, true);
        }

        public Builder slash(float min, float max) {
            raw = raw.withTag(slash, new StatsRange(min, max));
            return this;
        }
        public Builder blunt(float min, float max) {
            raw = raw.withTag(blunt, new StatsRange(min, max));
            return this;
        }
        public Builder puncture(float min, float max) {
            raw = raw.withTag(puncture, new StatsRange(min, max));
            return this;
        }
        public Builder art(float min, float max) {
            raw = raw.withTag(art, new StatsRange(min, max));
            return this;
        }

        public WeaponItem build() {
            return new WeaponItem(raw);
        }
    }
}
