package com.joedobo27.rs.templates;

import com.joedobo27.rs.*;
import com.joedobo27.rs.items.*;

import javax.json.JsonObject;

/**
 * class fields to match Recipes.checkRecipeSchema()
 */
public class Target implements Constants {

    private final ItemTemplate itemTemplate;
    /**
     * any cooked state.
     */
    private final CookedState cookedState;
    /**
     * any prepared state.
     */
    private final PreparedState preparedState;
    /**
     * see Materials.convertMaterialStringIntoByte().
     */
    private final Material material;
    /**
     * Can be any item template.
     */
    private final ItemTemplate realTemplate;
    private final Integer difficulty;
    private final Integer loss;
    private final Integer ratio;
    private final Creature creature;
    private final Rarity rarity;

    public Target(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                  ItemTemplate realTemplate, int difficulty, int loss, int ratio, Creature creature, Rarity rarity) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.loss = loss;
        this.ratio = ratio;
        this.creature = creature;
        this.rarity = rarity;
    }

    public Target(ItemTemplate itemTemplate, RecipeTemplate recipeTemplate) {
        this.itemTemplate = itemTemplate;
        this.cookedState = recipeTemplate.getTarget().cookedState;
        this.preparedState = recipeTemplate.getTarget().preparedState;
        this.material = recipeTemplate.getTarget().material;
        this.realTemplate = recipeTemplate.getTarget().realTemplate;
        this.difficulty = recipeTemplate.getTarget().difficulty;
        this.loss = recipeTemplate.getTarget().loss;
        this.ratio = recipeTemplate.getTarget().ratio;
        this.creature = recipeTemplate.getTarget().creature;
        this.rarity = recipeTemplate.getTarget().rarity;
    }

    public Target(RecipeTemplate recipeTemplate) {
        this.itemTemplate = recipeTemplate.getTarget().itemTemplate;
        this.cookedState = recipeTemplate.getTarget().cookedState;
        this.preparedState = recipeTemplate.getTarget().preparedState;
        this.material = recipeTemplate.getTarget().material;
        this.realTemplate = recipeTemplate.getTarget().realTemplate;
        this.difficulty = recipeTemplate.getTarget().difficulty;
        this.loss = recipeTemplate.getTarget().loss;
        this.ratio = recipeTemplate.getTarget().ratio;
        this.creature = recipeTemplate.getTarget().creature;
        this.rarity = recipeTemplate.getTarget().rarity;
    }

    public Target(Target target, Rarity rarity){
        this.itemTemplate = target.itemTemplate;
        this.cookedState = target.cookedState;
        this.preparedState = target.preparedState;
        this.material = target.material;
        this.realTemplate = target.realTemplate;
        this.difficulty = target.difficulty;
        this.loss = target.loss;
        this.ratio = target.ratio;
        this.creature = target.creature;
        this.rarity = rarity;
    }

    static Target buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate;
        CookedState cookedState = CookedState.NONE;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;
        Creature creature = Creature.NONE;
        Rarity rarity = Rarity.NONE;
        if (jsonObject.containsKey("id"))
            itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("id"));
        else
            throw new RuntimeException("Always has id");
        if (jsonObject.containsKey("cstate")){
            cookedState = CookedState.getCookedStateFromName(jsonObject.getString("cstate"));
        }
        if (jsonObject.containsKey("pstate")){
            preparedState = PreparedState.getPreparedStateFromName(jsonObject.getString("pstate"));
        }
        if (jsonObject.containsKey("material")){
            material = Material.getMaterialFromName(jsonObject.getString("material"));
        }
        if (jsonObject.containsKey("realtemplate")){
            realTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("realtemplate"));
        }
        if (jsonObject.containsKey("difficulty")){
            difficulty = jsonObject.getInt("difficulty");
        }
        if (jsonObject.containsKey("loss")){
            loss = jsonObject.getInt("loss");
        }
        if (jsonObject.containsKey("ratio")){
            ratio = jsonObject.getInt("ratio");
        }

        return new Target(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio,
                creature, rarity);
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }
}
