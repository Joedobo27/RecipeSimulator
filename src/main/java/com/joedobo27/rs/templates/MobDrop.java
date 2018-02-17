package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.Creature;
import com.joedobo27.rs.items.Rarity;

public class MobDrop implements Constants{

    private final Creature creature;
    private final Rarity rarity;

    public MobDrop(Creature creature, Rarity rarity) {
        this.creature = creature;
        this.rarity = rarity;
    }
}
