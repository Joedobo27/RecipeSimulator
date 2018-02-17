package com.joedobo27.rs.simulation;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;
import com.joedobo27.rs.items.PreparedState;
import com.joedobo27.rs.templates.Ingredient;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class IngredientExclusion implements Constants {

    private final Ingredient ingredient;

    private IngredientExclusion(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public static ArrayList<IngredientExclusion> jsonArrayToIngredientExclusion(JsonArray jsonArray) {
        ArrayList<IngredientExclusion> toReturn = new ArrayList<>();

        IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .forEach(jsonObject -> {
                    ItemTemplate itemTemplate = ItemTemplate.NONE;
                    CookedState cookedState = CookedState.ANY;
                    PreparedState preparedState = PreparedState.ANY;
                    Material material = Material.ANY;
                    ItemTemplate realTemplate = ItemTemplate.NONE;
                    if (jsonObject.containsKey("item"))
                        itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("item"));
                    if (jsonObject.containsKey("cstate")){
                        cookedState = CookedState.getCookedStateFromName(jsonObject.getString("cstate"));
                    }
                    if (jsonObject.containsKey("pstate")){
                        preparedState = PreparedState.getPreparedStateFromName(jsonObject.getString("pstate"));
                    }
                    if (jsonObject.containsKey("material")){
                        material = Material.getMaterialFromName(jsonObject.getString("material"));
                    }
                    toReturn.add(new IngredientExclusion(
                            new Ingredient(itemTemplate, cookedState, preparedState, material, realTemplate, DIFFICULTY_NONE,
                                    LOSS_NONE, RATIO_NONE, 1)));
                });
        return toReturn;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}
