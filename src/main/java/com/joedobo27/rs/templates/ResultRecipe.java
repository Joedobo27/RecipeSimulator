package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.CookedState;
import com.joedobo27.rs.Ingredient;
import com.joedobo27.rs.PreparedState;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;

import javax.json.JsonObject;

class ResultRecipe extends Ingredient implements Constants {

    /**
     * Must references either the target item or an item in the mandatory group.
     */
    private final ItemTemplate referenceMaterial;
    private final ItemTemplate referenceRealTemplate;
    private final String description;
    private final Boolean useTemplateWeight;

    static final ResultRecipe NO_RESULT_RECIPE = new ResultRecipe(ItemTemplate.NONE, CookedState.RAW, PreparedState.none,
            Material.NONE, ItemTemplate.NONE, 0, ItemTemplate.NONE, ItemTemplate.NONE,"",
            false);

    private ResultRecipe(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                         ItemTemplate realTemplate, int difficulty, ItemTemplate referenceMaterial, ItemTemplate referenceRealTemplate,
                         String description, Boolean useTemplateWeight) {
        super(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty);

        this.referenceMaterial = referenceMaterial;
        this.referenceRealTemplate = referenceRealTemplate;
        this.description = description;
        this.useTemplateWeight = useTemplateWeight;
    }

    static ResultRecipe buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        PreparedState preparedState = PreparedState.none;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        ItemTemplate referenceMaterial = ItemTemplate.NONE;
        ItemTemplate referenceRealTemplate = ItemTemplate.NONE;
        String description = "";
        Boolean useTemplateWeight = false;
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
        if (jsonObject.containsKey("refmaterial")){

        }
        if (jsonObject.containsKey("refrealtemplate")){

        }
        if (jsonObject.containsKey("description")){

        }
        if (jsonObject.containsKey("usetemplateweight")){

        }
        return new ResultRecipe(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, referenceMaterial,
                referenceRealTemplate, description, useTemplateWeight);
    }

}
