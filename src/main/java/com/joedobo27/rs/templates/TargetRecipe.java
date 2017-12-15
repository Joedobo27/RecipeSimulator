package com.joedobo27.rs.templates;

import com.joedobo27.rs.*;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;

import javax.json.JsonObject;

class TargetRecipe extends Ingredient implements Constants {

    private final Integer loss;
    private final Integer ratio;
    private final Creature creature;

    static final TargetRecipe NO_TARGET_RECIPE = new TargetRecipe(ItemTemplate.NONE, CookedState.RAW, PreparedState.none,
            Material.NONE, ItemTemplate.NONE, 0, 0, 0, Creature.NONE);

    private TargetRecipe(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                        ItemTemplate realTemplate, int difficulty, int loss, int ratio, Creature creature) {
        super(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty);
        this.loss = loss;
        this.ratio = ratio;
        this.creature = creature;
    }

    static TargetRecipe buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        PreparedState preparedState = PreparedState.none;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;
        Creature creature = Creature.NONE;
        if (jsonObject.containsKey("id"))
            itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("id"));
        if (jsonObject.containsKey("cstate")){

        }
        if (jsonObject.containsKey("pstate")){

        }
        if (jsonObject.containsKey("material")){

        }
        if (jsonObject.containsKey("realtemplate")){

        }
        if (jsonObject.containsKey("difficulty")){

        }
        if (jsonObject.containsKey("loss")){

        }
        if (jsonObject.containsKey("ratio")){

        }

        return new TargetRecipe(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio,
                creature);
    }
}
