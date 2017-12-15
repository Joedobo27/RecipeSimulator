package com.joedobo27.rs;

import com.joedobo27.rs.templates.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

class RecipeImportFactory {

    static ArrayList<Recipe> recipes = new ArrayList<>();

    static void importAllRecipe(String fileFolderPath){
        RecipeImportFactory jsonImporter = new RecipeImportFactory();
        File fileFolder = new File(fileFolderPath);
        File[] files = fileFolder.listFiles();
        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = jsonImporter.getFileReader(file);
                    JsonReader jsonReader = Json.createReader(reader);
                    JsonObject jsonObject = jsonReader.readObject();
                    jsonImporter.recipeImporter(jsonObject);
                    });
    }

    private Reader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void recipeImporter(JsonObject jsonObject) {
        String name = "";
        int recipeid = -1;
        boolean isKnown = false;
        boolean isNameable = false;
        Skill skill = Skill.NONE;
        ArrayList<CookerRecipe> cookers = new ArrayList<>();
        cookers.add(CookerRecipe.COOKER_RECIPE_EMPTY);
        ArrayList<ContainerRecipe> containers = new ArrayList<>();
        containers.add(ContainerRecipe.CONTAINER_RECIPE_EMPTY);
        Trigger trigger = Trigger.NONE;
        Active active = Active.ACTIVE_NONE;
        TargetRecipe targetRecipe = TargetRecipe.NO_TARGET_RECIPE;
        ResultRecipe resultRecipe = ResultRecipe.NO_RESULT_RECIPE;
        ArrayList<IngredientGroupRecipe> ingredientGroupRecipes = new ArrayList<>();
        ingredientGroupRecipes.add(IngredientGroupRecipe.INGREDIENT_GROUP_RECIPE_NONE);
        MobDropRecipe mobDropRecipe = MobDropRecipe.NO_MOB_DROP_RECIPE;

        if (jsonObject.containsKey("name"))
            name = jsonObject.getString("name");
        if (jsonObject.containsKey("recipeid"))
            recipeid = Integer.parseInt(jsonObject.getString("recipeid"));
        if (jsonObject.containsKey("known"))
            isKnown = jsonObject.getBoolean("known");
        if (jsonObject.containsKey("nameable"))
            isNameable = jsonObject.getBoolean("nameable");
        if (jsonObject.containsKey("skill"))
            skill = Skill.getSkillFromString(jsonObject.getString("skill"));
        if (jsonObject.containsKey("cookers")) {
            if (jsonObject.get("containers").getValueType() == JsonObject.ValueType.ARRAY) {
                cookers = (CookerRecipe.getCookersFromString(jsonObject.getJsonArray("cookers")));
            }
            else
            {
                int i = 1;
            }
        }
        if (jsonObject.containsKey("containers")){
            if (jsonObject.get("containers").getValueType() == JsonObject.ValueType.ARRAY){
                containers = ContainerRecipe.getContainersFromString(jsonObject.getJsonArray("containers"));
            }
            else
            {
                int i = 1;
            }
        }
        if (jsonObject.containsKey("trigger")){

        }
        if (jsonObject.containsKey("active"))
            active = Active.buildFromJson(jsonObject.getJsonObject("active"));
        if (jsonObject.containsKey("target"))
            targetRecipe = TargetRecipe.buildFromJson(jsonObject.getJsonObject("target"));
        if (jsonObject.containsKey("ingredients")){
            ingredientGroupRecipes = IngredientGroupRecipe.buildFromJson(jsonObject.getJsonObject("ingredients"));
        }
        if (jsonObject.containsKey("result")){
            resultRecipe = ResultRecipe.buildFromJson(jsonObject.getJsonObject("result"));
        }
        recipes.add(new Recipe(name, recipeid, isKnown, isNameable, skill, cookers, containers,
    trigger, active, targetRecipe, resultRecipe, ingredientGroupRecipes, mobDropRecipe));
}
}
