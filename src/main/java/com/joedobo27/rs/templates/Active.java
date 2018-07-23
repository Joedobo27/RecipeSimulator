package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.*;

import javax.json.JsonObject;

/**
 * class fields to match Recipes.checkRecipeSchema()
 */
public class Active implements Constants {

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
    private final Rarity rarity;

    public Active(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                  ItemTemplate realTemplate, Integer difficulty, Integer loss, Integer ratio, Rarity rarity) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.loss = loss;
        this.ratio = ratio;
        this.rarity = rarity;
    }

    public Active(ItemTemplate itemTemplate, RecipeTemplate recipeTemplate) {
        this.itemTemplate = itemTemplate;
        this.cookedState = recipeTemplate.getActive().cookedState;
        this.preparedState = recipeTemplate.getActive().preparedState;
        this.material = recipeTemplate.getActive().material;
        this.realTemplate = recipeTemplate.getActive().realTemplate;
        this.difficulty = recipeTemplate.getActive().difficulty;
        this.loss = recipeTemplate.getActive().loss;
        this.ratio = recipeTemplate.getActive().ratio;
        this.rarity = recipeTemplate.getActive().rarity;
    }

    public Active(RecipeTemplate recipeTemplate) {
        this.itemTemplate = recipeTemplate.getActive().itemTemplate;
        this.cookedState = recipeTemplate.getActive().cookedState;
        this.preparedState = recipeTemplate.getActive().preparedState;
        this.material = recipeTemplate.getActive().material;
        this.realTemplate = recipeTemplate.getActive().realTemplate;
        this.difficulty = recipeTemplate.getActive().difficulty;
        this.loss = recipeTemplate.getActive().loss;
        this.ratio = recipeTemplate.getActive().ratio;
        this.rarity = recipeTemplate.getActive().rarity;
    }

    public Active(Active active, Rarity rarity){
        this.itemTemplate = active.itemTemplate;
        this.cookedState = active.cookedState;
        this.preparedState = active.preparedState;
        this.material = active.material;
        this.realTemplate = active.realTemplate;
        this.difficulty = active.difficulty;
        this.loss = active.loss;
        this.ratio = active.ratio;
        this.rarity = rarity;
    }

    static Active buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate;
        CookedState cookedState = CookedState.NONE;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;

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
        return new Active(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio,
                Rarity.ANY);
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public CookedState getCookedState() {
        return cookedState;
    }

    public PreparedState getPreparedState() {
        return preparedState;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemTemplate getRealTemplate() {
        return realTemplate;
    }

    public Integer getLoss() {
        return loss;
    }

    public Integer getRatio() {
        return ratio;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
