package com.joedobo27.rs;

import com.joedobo27.rs.items.Rarity;

public class MobDropRecipe {

    private final Creature creature;
    private final Rarity rarity;

    public static final MobDropRecipe NO_MOB_DROP_RECIPE = new MobDropRecipe(Creature.NONE, Rarity.COMMON);

    public MobDropRecipe(Creature creature, Rarity rarity) {
        this.creature = creature;
        this.rarity = rarity;
    }
}
