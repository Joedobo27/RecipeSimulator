package com.joedobo27.rs.templates;


import com.joedobo27.rs.Constants;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IngredientGroup implements Constants{

    private final _IngredientGroup ingredientGroup;
    private final ArrayList<Ingredient> ingredients;

    public IngredientGroup(_IngredientGroup ingredientGroup, ArrayList<Ingredient> ingredients) {
        this.ingredientGroup = ingredientGroup;
        this.ingredients = ingredients;
    }

    static ArrayList<IngredientGroup> buildFromJson(JsonObject jsonObject) {
        IngredientGroup mandatory = IngredientGroup.INGREDIENT_GROUP_NONE;
        ArrayList<IngredientGroup> zeroOrOne = INGREDIENT_GROUPS_NONE;
        ArrayList<IngredientGroup> oneOf = INGREDIENT_GROUPS_NONE;
        ArrayList<IngredientGroup> oneOrMore = INGREDIENT_GROUPS_NONE;
        IngredientGroup optional = IngredientGroup.INGREDIENT_GROUP_NONE;
        IngredientGroup any = IngredientGroup.INGREDIENT_GROUP_NONE;

        if (jsonObject.containsKey("mandatory")) {
            if (jsonObject.get("mandatory").getValueType() == JsonObject.ValueType.ARRAY) {
                ArrayList<Ingredient> ingredients = jsonArrayToIngredients(jsonObject.getJsonArray("mandatory"));
                mandatory = new IngredientGroup(_IngredientGroup.MANDATORY, ingredients);
            }
        }
        if (jsonObject.containsKey("zeroorone")) {
            if (jsonObject.get("zeroorone").getValueType() == JsonObject.ValueType.ARRAY) {
                JsonArray jsonArray = jsonObject.getJsonArray("zeroorone");
                jsonArray.stream().map(jsonValue -> (JsonObject) jsonValue)
                        .forEach(jsonObject1 -> zeroOrOne.add(new IngredientGroup(_IngredientGroup.ZEROORONE,
                                    jsonArrayToIngredients(jsonObject1.getJsonArray("list")))));
            }
        }
        if (jsonObject.containsKey("oneof")) {
            if (jsonObject.get("oneof").getValueType() == JsonObject.ValueType.ARRAY){
                JsonArray jsonArray = jsonObject.getJsonArray("oneof");
                jsonArray.stream().map(jsonValue -> (JsonObject) jsonValue)
                        .forEach(jsonObject1 -> oneOf.add(new IngredientGroup(_IngredientGroup.ONEOF,
                                jsonArrayToIngredients(jsonObject1.getJsonArray("list")))));
            }
        }
        if (jsonObject.containsKey("oneormore")){
            if (jsonObject.get("oneormore").getValueType() == JsonObject.ValueType.ARRAY){
                JsonArray jsonArray = jsonObject.getJsonArray("oneormore");
                jsonArray.stream().map(jsonValue -> (JsonObject) jsonValue)
                        .forEach(jsonObject1 -> oneOrMore.add(new IngredientGroup(_IngredientGroup.ONEORMORE,
                                jsonArrayToIngredients(jsonObject1.getJsonArray("list")))));
            }
        }
        if (jsonObject.containsKey("optional")){
            if (jsonObject.get("optional").getValueType() == JsonObject.ValueType.ARRAY){
                ArrayList<Ingredient> ingredients = jsonArrayToIngredients(jsonObject.getJsonArray("optional"));
                optional = new IngredientGroup(_IngredientGroup.OPTIONAL, ingredients);
            }

        }
        if (jsonObject.containsKey("any")){
            if (jsonObject.get("any").getValueType() == JsonObject.ValueType.ARRAY){
                ArrayList<Ingredient> ingredients = jsonArrayToIngredients(jsonObject.getJsonArray("any"));
                any = new IngredientGroup(_IngredientGroup.ANY, ingredients);
            }

        }
        ArrayList<IngredientGroup> ingredientGroups = new ArrayList<>();
        if (mandatory != INGREDIENT_GROUP_NONE)
            ingredientGroups.add(mandatory);
        if (!zeroOrOne.isEmpty())
            ingredientGroups.addAll(zeroOrOne);
        if (!oneOf.isEmpty())
            ingredientGroups.addAll(oneOf);
        if (!oneOrMore.isEmpty())
            ingredientGroups.addAll(oneOrMore);
        if (optional != INGREDIENT_GROUP_NONE)
            ingredientGroups.add(optional);
        if (any != INGREDIENT_GROUP_NONE)
            ingredientGroups.add(any);
        return ingredientGroups;
    }

    static private ArrayList<Ingredient> jsonArrayToIngredients(JsonArray jsonArray) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(Ingredient::buildFromJson)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public _IngredientGroup getIngredientGroup() {
        return ingredientGroup;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }


    public enum _IngredientGroup {
        NONE,
        MANDATORY,
        OPTIONAL,
        ONEOF,
        ZEROORONE,
        ONEORMORE,
        ANY;

        public static final String ingredientGroupJsonLabel ="ingredients group";

        String getName() {
            return this.name().toLowerCase().replace("_", " ");
        }

        /**
         * @return true group can have multiple groups, each stored in its own list.
         */
        boolean canHaveListSubGroup(){
            return this == ONEOF || this == ZEROORONE || this == ONEORMORE;
        }
    }
}
