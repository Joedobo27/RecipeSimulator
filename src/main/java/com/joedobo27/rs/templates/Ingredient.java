package com.joedobo27.rs.templates;


import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.*;

import javax.annotation.Nonnull;
import javax.json.JsonObject;

public class Ingredient implements Constants, Comparable<Ingredient> {

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
    private final Integer amount;

    public Ingredient(Result result, Material material, Integer loss, Integer ratio, Integer amount) {
        this.itemTemplate = result.getItemTemplate();
        this.cookedState = result.getCookedState();
        this.preparedState = result.getPreparedState();
        this.material = material;
        this.realTemplate = result.getRealTemplate();
        this.difficulty = result.getDifficulty();
        this.loss = loss;
        this.ratio = ratio;
        this.amount = amount;
    }

    public Ingredient(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                      ItemTemplate realTemplate, Integer difficulty, Integer loss, Integer ratio, Integer amount) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.loss = loss;
        this.ratio = ratio;
        this.amount = amount;
    }

    public static Ingredient buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate;
        CookedState cookedState = CookedState.NONE;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;
        Integer amount = AMOUNT_NONE;
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
        if (jsonObject.containsKey("amount")){
            amount = jsonObject.getInt("amount");
        }
        return new Ingredient(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss,
                ratio, amount);
    }


    public ItemTemplate getItemTemplate() {
        return itemTemplate;
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

    public boolean equalsResult(RecipeTemplate recipeTemplate) {
        Result result = recipeTemplate.getResult();
        return itemTemplate == result.getItemTemplate() && cookedState == result.getCookedState() &&
                preparedState == result.getPreparedState() && material == result.getMaterial();
    }

    @Override public boolean equals(Object other) {
        if (!(other instanceof Ingredient)) {
            return false;
        }
        Ingredient otherIngredient = (Ingredient) other;
        return itemTemplate == otherIngredient.itemTemplate && cookedState == otherIngredient.cookedState &&
                preparedState == otherIngredient.preparedState && material == otherIngredient.material;
    }

    @Override public int hashCode() {
        return material.ordinal() * 31 + cookedState.ordinal() * 17 + preparedState.ordinal() * 7 +
                itemTemplate.ordinal() * 5;
    }

    @Override
    public int compareTo(Ingredient other) {
        return itemTemplate != other.itemTemplate ? itemTemplate.compareTo(other.itemTemplate) :
                cookedState != other.cookedState ? cookedState.compareTo(other.cookedState) :
                        preparedState != other.preparedState ? preparedState.compareTo(other.preparedState) :
                                material.compareTo(other.material);
    }

    public ItemTemplate getRealTemplate() {
        return realTemplate;
    }
}
