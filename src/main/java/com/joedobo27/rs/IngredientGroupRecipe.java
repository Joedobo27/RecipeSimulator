package com.joedobo27.rs;


import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class IngredientGroupRecipe {

    private final IngredientGroup ingredientGroup;
    private final ArrayList<IngredientRecipe> ingredients;

    static final IngredientGroupRecipe INGREDIENT_GROUP_RECIPE_NONE = new IngredientGroupRecipe(IngredientGroup.NONE,
            new ArrayList<>(Arrays.asList(IngredientRecipe.NO_INGREDIENT_RECIPE)));

    public IngredientGroupRecipe(IngredientGroup ingredientGroup, ArrayList<IngredientRecipe> ingredients) {
        this.ingredientGroup = ingredientGroup;
        this.ingredients = ingredients;
    }

    static ArrayList<IngredientGroupRecipe> buildFromJson(JsonObject jsonObject) {
        ArrayList<IngredientRecipe> mandatory = new ArrayList<>();
        mandatory.add(IngredientRecipe.NO_INGREDIENT_RECIPE);
        ArrayList<IngredientRecipe> optional = new ArrayList<>();
        optional.add(IngredientRecipe.NO_INGREDIENT_RECIPE);
        ArrayList<IngredientRecipe> any = new ArrayList<>();
        any.add(IngredientRecipe.NO_INGREDIENT_RECIPE);

        if (jsonObject.containsKey("mandatory")) {
            if (jsonObject.get("mandatory").getValueType() == JsonObject.ValueType.ARRAY) {
                mandatory = jsonArrayToIngredients(jsonObject.getJsonArray("mandatory"));
            }
        }
        if (jsonObject.containsKey("zeroorone")) {
            if (jsonObject.get("zeroorone").getValueType() == JsonObject.ValueType.ARRAY) {
                int i = 1;
            }

        }
        if (jsonObject.containsKey("oneof")) {
            if (jsonObject.get("oneof").getValueType() == JsonObject.ValueType.ARRAY){
                int i = 1;
            }

        }
        if (jsonObject.containsKey("oneormore")){
            if (jsonObject.get("oneormore").getValueType() == JsonObject.ValueType.ARRAY){
                int i = 1;
            }

        }
        if (jsonObject.containsKey("optional")){
            if (jsonObject.get("optional").getValueType() == JsonObject.ValueType.ARRAY){
                optional = jsonArrayToIngredients(jsonObject.getJsonArray("optional"));
            }

        }
        if (jsonObject.containsKey("any")){
            if (jsonObject.get("any").getValueType() == JsonObject.ValueType.ARRAY){
                any = jsonArrayToIngredients(jsonObject.getJsonArray("any"));
            }

        }
        return null;
    }

    static private ArrayList<IngredientRecipe> jsonArrayToIngredients(JsonArray jsonArray) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(IngredientRecipe::buildFromJson)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
