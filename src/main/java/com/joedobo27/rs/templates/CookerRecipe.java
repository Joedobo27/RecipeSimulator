package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.Cooker;
import com.joedobo27.rs.items.Rarity;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CookerRecipe implements Constants {

    private final Cooker cooker;
    private final Integer difficulty;
    private final Rarity rarity;
    static final CookerRecipe COOKER_RECIPE_EMPTY = new CookerRecipe(Cooker.NONE, DIFFICULTY_NONE, Rarity.COMMON);

    static public CookerRecipe buildRecipeCooker(Cooker cooker, Integer difficulty, Rarity rarity) {
        return new CookerRecipe(cooker, difficulty, rarity);
    }

    static ArrayList<CookerRecipe> getCookersFromString(JsonArray jsonArray) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(CookerRecipe::jsonObjectToContainer)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static private CookerRecipe jsonObjectToContainer(JsonObject jsonObject) {
        Cooker cooker = Cooker.NONE;
        Integer difficulty = 0;
        Rarity rarity = Rarity.COMMON;
        if (jsonObject.containsKey("id")){
            cooker = Cooker.getCookerFromName(jsonObject.getString("id"));

        }
        if (jsonObject.containsKey("difficulty")){
            difficulty = jsonObject.getInt("difficulty");
        }
        return new CookerRecipe(cooker, difficulty, rarity);
    }

    private CookerRecipe(Cooker cooker, int difficulty, Rarity rarity) {
        this.cooker = cooker;
        this.difficulty = difficulty;
        this.rarity = rarity;
    }

    public Cooker getCooker() {
        return cooker;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
