package com.joedobo27.rs;

import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.Cooker;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;

import javax.json.JsonObject;

class IngredientRecipe extends Ingredient implements Constants {

    private final int loss;
    private final int ratio;
    private final int amount;

    static final IngredientRecipe NO_INGREDIENT_RECIPE = new IngredientRecipe(ItemTemplate.NONE, CookedState.RAW,
            Cooker.PreparedState.none, Material.NONE, ItemTemplate.NONE, 0, 0, 0, 0);

    private IngredientRecipe(ItemTemplate itemTemplate, CookedState cookedState, Cooker.PreparedState preparedState, Material material, ItemTemplate realTemplate, int difficulty, int loss, int ratio, int amount) {
        super(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty);
        this.loss = loss;
        this.ratio = ratio;
        this.amount = amount;
    }

    static IngredientRecipe buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        Cooker.PreparedState preparedState = Cooker.PreparedState.none;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;
        Integer amount = AMOUNT_NONE;

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
        if (jsonObject.containsKey("amount")){

        }
        return new IngredientRecipe(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio,
                amount);
    }
}
