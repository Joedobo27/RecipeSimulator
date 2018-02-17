package com.joedobo27.rs.simulation;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;
import com.joedobo27.rs.items.PreparedState;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ExclusionFactory implements Constants {

    private static ArrayList<CookerExclusion> cookerExclusions = new ArrayList<>();
    private static ArrayList<IngredientExclusion> ingredientExclusions = new ArrayList<>();


    public static void importExclusions(InputStream inputStream) {
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        if (jsonObject.containsKey("cooker")) {
            cookerExclusions = CookerExclusion.jsonArrayToCookerExclusion(jsonObject.getJsonArray("cooker"));
        }
        if (jsonObject.containsKey("Ingredient")) {
            ingredientExclusions = IngredientExclusion.jsonArrayToIngredientExclusion(jsonObject.getJsonArray("Ingredient"));
        }
    }

    public static boolean hasCookerExclusions() {
        return !cookerExclusions.isEmpty();
    }

    public static boolean hasIngredientExclusions() {
        return !ingredientExclusions.isEmpty();
    }

    public static ArrayList<CookerExclusion> getCookerExclusions() {
        return cookerExclusions;
    }

    public static ArrayList<IngredientExclusion> getIngredientExclusions() {
        return ingredientExclusions;
    }

    public static ArrayList<ItemTemplate> getItemOnlyExclusions() {
        return ingredientExclusions.stream()
                .filter(ingredientExclusion -> ingredientExclusion.getIngredient().getItemTemplate() != ItemTemplate.NONE &&
                        ingredientExclusion.getIngredient().getCookedState() == CookedState.ANY &&
                        ingredientExclusion.getIngredient().getPreparedState() == PreparedState.ANY &&
                        ingredientExclusion.getIngredient().getMaterial() == Material.ANY)
                .map(ingredientExclusion -> ingredientExclusion.getIngredient().getItemTemplate())
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
