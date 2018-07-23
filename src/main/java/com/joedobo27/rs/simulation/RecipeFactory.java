package com.joedobo27.rs.simulation;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.*;
import com.joedobo27.rs.templates.*;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeFactory implements Constants{


    /**
     * get active item from template and multiply by 4 for rarity. This is a choose one.
     */
    private final ArrayList<Active> actives;
    /**
     * get target item from template and multiply by 4 for rarity. This is a choose one.
     */
    private final ArrayList<Target> targets;
    /**
     * get cookers from template and multiply by 4 for rarity. This is a choose one.
     */
    private final ArrayList<Cooker> cookers;
    /**
     * get container from template and Rarity doesn't matter with one.
     */
    private final ArrayList<Container> containers;
    /**
     * Each mandatory group entry from templates.RecipeTemplate gets its own ArrayList of potential substitute ingredients.
     * Choose one ingredient from each substitute set.
     */
    private final ArrayList<ArrayList<Ingredient>> mandatory;
    /**
     * get ingredients from template zeroorone groups. For a given zero. list/group here, make all possible combinations and choose one.
     * Keep in mind that there can be multiple list or groupings of zeroorone.
     */
    private final ArrayList<ArrayList<Ingredient>> zeroOrOne;
    /**
     * get ingredients from template oneof groups. For a given oneof list/group here, make all possible combinations and choose one.
     * Keep in mind that there can be multiple list or groupings of oneof.
     */
    private final ArrayList<ArrayList<Ingredient>> oneOf;
    /**
     * get ingredients from template oneormore groups. For a given oneormore list/group here, make all possible combinations
     * and choose as many will fit in the container.
     * Keep in mind that there can be multiple list or groupings of oneof.
     * Because of the variable number of potiential choose elements, it might be needed to see how many of the smallest
     * one would fit and use that for all. Finaly, remove or prequalify combinations not working with container volume.
     */
    private final ArrayList<ArrayList<Ingredient>> oneOrMore;
    /**
     * list all, choose one.
     */
    private final ArrayList<ArrayList<Ingredient>> optional;
    /**
     * Any and all combinations possible, unordered and repeats.
     */
    final ArrayList<ArrayList<Ingredient>> any;

    /**
     * see Ingredient.matches(Item) for rules on when something is a substitute or matching ingredient for a recipes.
     * return (
     *      this.itemTemplate.isFoodGroup() &&
     *      item.getTemplate().getFoodGroup() == this.itemTemplate.getTemplateId() ||
     *      this.getTemplateId() == item.getTemplateId()
     *      ) &&
     *      this.checkState(item) && this.checkMaterial(item) && this.checkRealTemplate(item) && this.checkCorpseData(item);
     *
     * @param cookers
     * @param containers
     * @param mandatory
     * @param zeroOrOne
     * @param oneOf
     * @param oneOrMore
     * @param optional
     * @param any
     */
    private RecipeFactory (ArrayList<Active> actives, ArrayList<Target> targets, ArrayList<Cooker> cookers, ArrayList<Container> containers,
                           ArrayList<ArrayList<Ingredient>> mandatory, ArrayList<ArrayList<Ingredient>> zeroOrOne,
                           ArrayList<ArrayList<Ingredient>> oneOf, ArrayList<ArrayList<Ingredient>> oneOrMore,
                           ArrayList<ArrayList<Ingredient>> optional, ArrayList<ArrayList<Ingredient>> any) {
        this.actives = actives;
        this.targets = targets;
        this.cookers = cookers;
        this.containers = containers;
        this.mandatory = mandatory;
        this.zeroOrOne = zeroOrOne;
        this.oneOf = oneOf;
        this.oneOrMore = oneOrMore;
        this.optional = optional;
        this.any = any;
    }


    public static void buildRecipe(RecipeTemplate recipeTemplate) {
        ArrayList<Active> actives = selectActives(recipeTemplate);
        actives = filterActives(actives);
        ArrayList<Target> targets = selectTargets(recipeTemplate);
        targets = filterTargets(targets);
        ArrayList<Cooker> cookers = selectCookers(recipeTemplate);
        cookers = filterCookers(cookers);
        ArrayList<Container> containers = new ArrayList<>(recipeTemplate.getContainers());
        ArrayList<ArrayList<Ingredient>> mandatory = new ArrayList<>();
        if (recipeTemplate.hasMandatoryGroup()) {
            mandatory = recipeTemplate.getMandatoryGroup().getIngredients().stream()
                    .map(RecipeFactory::buildMandatory)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        ArrayList<ArrayList<Ingredient>> oneOrMore = new ArrayList<>();
        if (recipeTemplate.hasOneOrMoreGroup()) {
            oneOrMore = recipeTemplate.getOneOrMoreGroups().stream()
                    .map(RecipeFactory::buildOneOrMore)
                    .collect(Collectors.toCollection(ArrayList::new));
            int i = 1;
        }
        //RecipeFactory recipeFactory = new RecipeFactory(cookers, containers, );
    }

    /**
     * From Item.calculateAndSaveNutrition() the only fields that mix up affinity are item template ID and rarity.
     *
     * @param recipeTemplate
     * @return
     */
    private static ArrayList<Active> selectActives(RecipeTemplate recipeTemplate) {

        ArrayList<Active> actives;
        // Get any recipe's result.itemTemplate which matches arg1's (recipeTemplate) active.itemTemplate.
        ArrayList<RecipeTemplate> recipeTemplates = RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate1 -> Objects.equals(recipeTemplate1.getResult().getItemTemplate(),
                        recipeTemplate.getActive().getItemTemplate()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (recipeTemplates.isEmpty()) {
            // If no recipe's result matches look through items that could be an item for active. see Recipes.readIngredient()
            actives = Arrays.stream(ItemTemplate.values())
                    .filter(itemTemplate -> itemTemplate.isFood() || itemTemplate.isLiquidCooking() ||
                            itemTemplate.isCookingTool() || itemTemplate.isRecipeItem())
                    .filter(itemTemplate -> Objects.equals(itemTemplate, recipeTemplate.getActive().getItemTemplate()))
                    .map(itemTemplate -> new Active(itemTemplate, recipeTemplate))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        else
            actives = recipeTemplates.stream()
                    .map(Active::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        if (actives.isEmpty())
            return ACTIVES_NONE;

        actives = actives.stream()
                .flatMap(active -> Arrays.stream(Rarity.values())
                        .filter(rarity -> !Objects.equals(rarity, Rarity.ANY) && !Objects.equals(rarity, Rarity.NONE))
                        .map(rarity -> new Active(active, rarity)))
                .collect(Collectors.toCollection(ArrayList::new));

        return actives;
    }

    private static ArrayList<Active> filterActives(ArrayList<Active> actives) {
        return null;
    }

    private static ArrayList<Target> selectTargets(RecipeTemplate recipeTemplate) {

        ArrayList<Target> targets;
        // Get any recipe's result.itemTemplate which matches arg1's (recipeTemplate) active.itemTemplate.
        ArrayList<RecipeTemplate> recipeTemplates = RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate1 -> Objects.equals(recipeTemplate1.getResult().getItemTemplate(),
                        recipeTemplate.getActive().getItemTemplate()))
                .collect(Collectors.toCollection(ArrayList::new));
        if (recipeTemplates.isEmpty()) {
            // If no recipe's result matches look through items that could be an item for active. see Recipes.readIngredient()
            targets = Arrays.stream(ItemTemplate.values())
                    .filter(itemTemplate -> itemTemplate.isFood() || itemTemplate.isLiquidCooking() ||
                            itemTemplate.isCookingTool() || itemTemplate.isRecipeItem())
                    .filter(itemTemplate -> Objects.equals(itemTemplate, recipeTemplate.getTarget().getItemTemplate()))
                    .map(itemTemplate -> new Target(itemTemplate, recipeTemplate))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        else
            targets = recipeTemplates.stream()
                    .map(Target::new)
                    .collect(Collectors.toCollection(ArrayList::new));
        if (targets.isEmpty())
            return TARGETS_NONE;

        targets = targets.stream()
                .flatMap(active -> Arrays.stream(Rarity.values())
                        .filter(rarity -> !Objects.equals(rarity, Rarity.ANY) && !Objects.equals(rarity, Rarity.NONE))
                        .map(rarity -> new Target(active, rarity)))
                .collect(Collectors.toCollection(ArrayList::new));

        return targets;
    }

    private static ArrayList<Target> filterTargets(ArrayList<Target> targets) {
        return null;
    }

    private static ArrayList<Cooker> selectCookers(RecipeTemplate recipeTemplate) {
        ArrayList<Cooker> cookers = recipeTemplate.getCookers();
        if (cookers.isEmpty() || (cookers.size() == 1 && Objects.equals(cookers.get(0), COOKER_NONE)))
            return COOKERS_NONE;
        cookers = cookers.stream()
                .flatMap(cooker -> Arrays.stream(Rarity.values())
                        .filter(rarity -> !Objects.equals(rarity, Rarity.ANY) && !Objects.equals(rarity, Rarity.NONE))
                .map(rarity -> new Cooker(cooker, rarity)))
                .collect(Collectors.toCollection(ArrayList::new));
        return cookers;
    }

    private static ArrayList<Cooker> filterCookers(ArrayList<Cooker> cookers) {
        if (ExclusionFactory.hasCookerExclusions()) {
            return cookers.stream()
                    .filter(cooker -> ExclusionFactory.getCookerExclusions().stream()
                            .noneMatch( cookerExclusion -> cookerExclusion.equals(cooker)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return cookers;
    }

    private static ArrayList<Cooker> buildCookerRarities(Cooker cooker) {
        ArrayList<Cooker> cookers = new ArrayList<>();
        Arrays.stream(Rarity.values())
                .forEach(rarity1 -> {
                    if (rarity1 != Rarity.ANY)
                        cookers.add(new Cooker(cooker.getCooker(), cooker.getDifficulty(), rarity1));
                });
        return cookers;
    }

    private static ArrayList<Ingredient> buildMandatory(Ingredient ingredient) {
        // 1. Does ingredient template match a RecipeTemplate.result itemTemplate? Return ArrayList<RecipeTemplate>
        ArrayList<RecipeTemplate> recipeTemplates = null;

        return getIngredientSubstitutes(ingredient);
    }

    private ArrayList<RecipeTemplate> getMatchingItemTemplates(Ingredient ingredient) {
        ArrayList<RecipeTemplate> recipeTemplates = RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate ->
                        Objects.equals(recipeTemplate.getResult().getItemTemplate(), ingredient.getItemTemplate())

                )
                .collect(Collectors.toCollection(ArrayList::new));

        return recipeTemplates;
    }

    private ArrayList<RecipeTemplate> getMatchingItemTemplates(RecipeTemplate recipeTemplate) {
        ArrayList<RecipeTemplate> recipeTemplates = RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate1 -> Objects.equals(recipeTemplate1.getResult().getItemTemplate(),
                        recipeTemplate.getResult().getItemTemplate())

                )
                .collect(Collectors.toCollection(ArrayList::new));

        return recipeTemplates;
    }

    private static ArrayList<Ingredient> buildOneOrMore(IngredientGroup ingredientGroup) {
        return ingredientGroup.getIngredients().stream()
                .map(RecipeFactory::getIngredientSubstitutes)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Ingredient> getIngredientSubstitutes(Ingredient ingredient) {
        ArrayList<ItemTemplate> itemTemplates = selectTemplates(ingredient);
        itemTemplates = filterTemplates(itemTemplates);

        ArrayList<RecipeTemplate> statesTemplates = selectRecipeFromResult(itemTemplates, ingredient);
        statesTemplates = filterRecipeByState(statesTemplates);

        ArrayList<Ingredient> ingredients = makeIngredientVariantsFromMaterials(statesTemplates, ingredient);

        return ingredients;
    }

    private static ArrayList<ItemTemplate> selectTemplates(Ingredient ingredient) {



        ArrayList<ItemTemplate> itemTemplates = new ArrayList<>(Collections.singletonList(ingredient.getItemTemplate()));
        if (ingredient.getItemTemplate().isAnyFoodGrouping()) {
            itemTemplates = ingredient.getItemTemplate().getCompatibleAnyTemplates();
            // 1. Meat templateId is 92, filetMeat templateID is 368, 368 mod 138 is 92 so they produce the same result.
            // 2. Cooked meat is a legacy item from before cooking system changes that can't be made any more.
            // 3. crab meat is very limited in its use. It usually ends up being meat, and type seafood after it's processed.
        }
        return itemTemplates;
    }

    private static ArrayList<ItemTemplate> filterTemplates(ArrayList<ItemTemplate> itemTemplates){
        return itemTemplates.stream()
                .filter(itemTemplate -> ExclusionFactory.getItemOnlyExclusions().stream()
                        .noneMatch(itemTemplateExclusion -> itemTemplateExclusion.equals(itemTemplate)
                        ))
                .filter(itemTemplate -> Arrays.stream(itemTemplate.getTypes())
                        .noneMatch(itemTemplateType -> itemTemplateType.equals(ItemTemplateType.NO_CREATE)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static  ArrayList<RecipeTemplate> selectRecipeFromResult(ArrayList<ItemTemplate> itemTemplates,
                                                                     Ingredient ingredient) {
        return RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate -> itemTemplates.stream()
                        .anyMatch(itemTemplate -> recipeTemplate.getResult().getItemTemplate().equals(itemTemplate)))
                .filter(recipeTemplate -> itemTemplates.stream()
                        .anyMatch(itemTemplate -> recipeTemplate.getResult().equals(ingredient)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<RecipeTemplate> filterRecipeByState(ArrayList<RecipeTemplate> statesTemplates) {
        ArrayList<IngredientExclusion> ingredientExclusions = ExclusionFactory.getIngredientExclusions().stream()
                .filter(ingredientExclusion -> !ingredientExclusion.getIngredient().getCookedState()
                        .equals(CookedState.ANY)
                        ||
                        !ingredientExclusion.getIngredient().getPreparedState().equals(PreparedState.ANY))
                .collect(Collectors.toCollection(ArrayList::new));
        return statesTemplates.stream()
                .filter(recipeTemplate -> ingredientExclusions.stream()
                        .noneMatch(ingredientExclusion ->
                                        (ingredientExclusion.getIngredient().getCookedState() != CookedState.ANY &&
                                                ingredientExclusion.getIngredient().getCookedState()
                                                        .equals(recipeTemplate.getResult().getCookedState()))
                                        ||
                                        (ingredientExclusion.getIngredient().getPreparedState() != PreparedState.ANY &&
                                                ingredientExclusion.getIngredient().getPreparedState()
                                                        .equals(recipeTemplate.getResult().getPreparedState()))
                                        )
                        )
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Ingredient> makeIngredientVariantsFromMaterials(ArrayList<RecipeTemplate> recipeTemplates,
                                                                             Ingredient ingredient) {
        ArrayList<Material> materials = new ArrayList<>();
        //recipeTemplates.stream().forEach( recipeTemplate -> recipeTemplate.getResult().getReferenceMaterial() == )

        if (ingredient.getItemTemplate().equals(ItemTemplate.ANY_MEAT)) {
            materials = Constants.MEAT_TYPES.stream()
                    .filter(material -> ExclusionFactory.getIngredientExclusions().stream()
                            .filter(ingredientExclusion -> !ingredientExclusion.getIngredient().getMaterial()
                                    .equals(Material.ANY))
                            .noneMatch(ingredientExclusion -> ingredientExclusion.getIngredient().getMaterial()
                                    .equals(material)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        ArrayList<Material> materials1 = materials;
        recipeTemplates.stream()
                .filter(recipeTemplate -> recipeTemplate.getResult().getMaterial().equals(Material.ANY))
                .forEach(recipeTemplate -> materials1.forEach(
                        material -> ingredients.add(new Ingredient(recipeTemplate.getResult(), material, LOSS_NONE,
                                RATIO_NONE, 1))));

        recipeTemplates.stream()
                .filter(recipeTemplate -> !recipeTemplate.getResult().getMaterial().equals(Material.ANY))
                .filter(recipeTemplate -> ingredients.stream()
                        .noneMatch(ingredient1 -> ingredient1.equalsResult(recipeTemplate)))
                .forEach(recipeTemplate -> ingredients.add(new Ingredient(recipeTemplate.getResult(),
                        recipeTemplate.getResult().getMaterial(), LOSS_NONE, RATIO_NONE, 1)));

        return ingredients;
    }
}
