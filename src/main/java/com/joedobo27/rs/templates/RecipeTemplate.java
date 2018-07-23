package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.Creature;
import com.joedobo27.rs.Skill;
import com.joedobo27.rs.items.*;
import com.joedobo27.rs.simulation.IngredientExclusion;

import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecipeTemplate implements Constants {

    @Nonnull
    private final String name;
    private final Integer recipeId;
    private final boolean known;
    private final boolean nameable;
    @Nonnull
    private final Skill skill;
    @Nonnull
    private final ArrayList<Cooker> cookers;
    @Nonnull
    private final ArrayList<Container> containers;
    @Nonnull
    private final Trigger trigger;
    @Nonnull
    private final Active active;
    @Nonnull
    private final Target target;
    @Nonnull
    private final Result result;
    @Nonnull
    private final ArrayList<IngredientGroup> ingredientGroups;
    @Nonnull
    private final MobDrop mobDropRecipe;

    private static ArrayList<RecipeTemplate> importedRecipeTemplates = new ArrayList<>();

    static {
        importedRecipeTemplates.add(new RecipeTemplate("slice of bread", -1, true, false,
                Skill.COOKING, COOKERS_NONE, CONTAINERS_NONE, Trigger.CREATE,
                new Active(ItemTemplate.KNIFE, CookedState.NONE, PreparedState.NONE, Material.ANY, ItemTemplate.NONE,
                        4, 0, 0, Rarity.ANY),
                new Target(ItemTemplate.BREAD, CookedState.ANY, PreparedState.NONE, Material.ANY, ItemTemplate.NONE,
                        15, 0, 0, Creature.NONE, Rarity.NONE),
                new Result(ItemTemplate.SLICE_OF_BREAD, CookedState.RAW, PreparedState.ANY, Material.ANY,
                        ItemTemplate.BREAD, 4, ItemTemplate.BREAD, ItemTemplate.NONE,
                        "A slice of bread from a loaf.", true),
                INGREDIENT_GROUPS_NONE, Constants.MOB_DROP_NONE));
    }

    public RecipeTemplate(@Nonnull String name, int recipeId, boolean known, boolean nameable, @Nonnull Skill skill,
                   @Nonnull ArrayList<Cooker> cookers, @Nonnull ArrayList<Container> containers,
                   @Nonnull Trigger trigger, @Nonnull Active active, @Nonnull Target target,
                   @Nonnull Result result, @Nonnull ArrayList<IngredientGroup> ingredientGroups,
                   @Nonnull MobDrop mobDropRecipe) {
        this.name = name;
        this.recipeId = recipeId;
        this.known = known;
        this.nameable = nameable;
        this.skill = skill;
        this.cookers = cookers;
        this.containers = containers;
        this.trigger = trigger;
        this.active = active;
        this.target = target;
        this.result = result;
        this.ingredientGroups = ingredientGroups;
        this.mobDropRecipe = mobDropRecipe;
    }


    public static void importAllRecipe(String fileFolderPath){
        File fileFolder = new File(fileFolderPath);
        File[] files = fileFolder.listFiles();
        if (files == null)
            return;
        Arrays.stream(files)
                .forEach(file -> {
                    Reader reader = getFileReader(file);
                    JsonReader jsonReader = Json.createReader(reader);
                    JsonObject jsonObject = jsonReader.readObject();
                    recipeImporter(jsonObject);
                });
    }

    public static void importRecipe(InputStream inputStream) {
        JsonReader jsonReader = Json.createReader(inputStream);
        JsonObject jsonObject = jsonReader.readObject();
        recipeImporter(jsonObject);
    }

    static private Reader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private void recipeImporter(JsonObject jsonObject) {
        String name = NAME_NONE;
        Integer recipeId = RECIPE_ID_NONE;
        boolean isKnown = false;
        boolean isNameable = false;
        Skill skill = Skill.NONE;
        ArrayList<Cooker> cookers = COOKERS_NONE;
        ArrayList<Container> containers = CONTAINERS_NONE;
        Trigger trigger = Trigger.NONE;
        Active active = Active.ACTIVE_NONE;
        Target targetRecipe = Target.TARGET_NONE;
        Result resultRecipe = Result.RESULT_NONE;
        ArrayList<IngredientGroup> ingredientGroup = new ArrayList<>();
        ingredientGroup.add(IngredientGroup.INGREDIENT_GROUP_NONE);
        MobDrop mobDropRecipe = MobDrop.MOB_DROP_NONE;

        if (jsonObject.containsKey("name"))
            name = jsonObject.getString("name");
        if (jsonObject.containsKey("recipeid"))
            recipeId = Integer.parseInt(jsonObject.getString("recipeid"));
        if (jsonObject.containsKey("known"))
            isKnown = jsonObject.getBoolean("known");
        if (jsonObject.containsKey("nameable"))
            isNameable = jsonObject.getBoolean("nameable");
        if (jsonObject.containsKey("skill"))
            skill = Skill.getSkillFromName(jsonObject.getString("skill"));
        if (jsonObject.containsKey("cookers")) {
            if (jsonObject.get("cookers").getValueType() == JsonObject.ValueType.ARRAY) {
                cookers = (Cooker.jsonArrayToCookers(jsonObject.getJsonArray("cookers")));
            }
        }
        if (jsonObject.containsKey("containers")){
            if (jsonObject.get("containers").getValueType() == JsonObject.ValueType.ARRAY){
                containers = Container.jsonArrayToContainers(jsonObject.getJsonArray("containers"));
            }
        }
        if (jsonObject.containsKey("trigger")){

        }
        if (jsonObject.containsKey("active"))
            active = Active.buildFromJson(jsonObject.getJsonObject("active"));
        if (jsonObject.containsKey("target"))
            targetRecipe = Target.buildFromJson(jsonObject.getJsonObject("target"));
        if (jsonObject.containsKey("ingredients")){
            ingredientGroup = IngredientGroup.buildFromJson(jsonObject.getJsonObject("ingredients"));
        }
        if (jsonObject.containsKey("result")){
            resultRecipe = Result.buildFromJson(jsonObject.getJsonObject("result"));
        }
        importedRecipeTemplates.add(new RecipeTemplate(name, recipeId, isKnown, isNameable, skill, cookers, containers,
                trigger, active, targetRecipe, resultRecipe, ingredientGroup, mobDropRecipe));
    }

    public static ArrayList<RecipeTemplate> getImportedRecipeTemplates() {
        return importedRecipeTemplates;
    }

    @Nonnull
    public ArrayList<Cooker> getCookers() {
        return cookers;
    }

    public boolean cookersIsNone() {
        return Objects.equals(this.cookers, COOKERS_NONE);
    }

    @Nonnull
    public ArrayList<Container> getContainers() {
        return containers;
    }

    @Nonnull
    public ArrayList<IngredientGroup> getIngredientGroups() {
        return ingredientGroups;
    }

    public boolean equalsIngredientExclusion(IngredientExclusion ingredientExclusion) {
        return true;
    }

    public boolean hasMandatoryGroup() {
        return ingredientGroups.stream()
                .filter(ingredientGroup -> ingredientGroup.getIngredientGroup() == IngredientGroup._IngredientGroup.MANDATORY)
                .anyMatch(ingredientGroup -> ingredientGroup != Constants.INGREDIENT_GROUP_NONE);
    }

    public IngredientGroup getMandatoryGroup() {
        return ingredientGroups.stream()
                .filter(ingredientGroup -> ingredientGroup.getIngredientGroup() == IngredientGroup._IngredientGroup.MANDATORY)
                .findFirst()
                .orElse(INGREDIENT_GROUP_NONE);
    }

    public boolean hasOneOrMoreGroup() {
        return ingredientGroups.stream()
                .filter(ingredientGroup -> ingredientGroup.getIngredientGroup() == IngredientGroup._IngredientGroup.ONEORMORE)
                .anyMatch(ingredientGroup -> ingredientGroup != Constants.INGREDIENT_GROUP_NONE);
    }

    public ArrayList<IngredientGroup> getOneOrMoreGroups() {
        return ingredientGroups.stream()
                .filter(ingredientGroup -> ingredientGroup.getIngredientGroup() == IngredientGroup._IngredientGroup.ONEORMORE)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Nonnull
    public Result getResult() {
        return result;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public Active getActive() {
        return active;
    }

    @Nonnull
    public Target getTarget() {
        return target;
    }
}
