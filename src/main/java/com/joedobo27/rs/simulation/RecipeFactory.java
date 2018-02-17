package com.joedobo27.rs.simulation;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.*;
import com.joedobo27.rs.templates.*;

import java.util.*;
import java.util.stream.Collectors;

public class RecipeFactory implements Constants{


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
    private RecipeFactory (ArrayList<Cooker> cookers, ArrayList<Container> containers, ArrayList<ArrayList<Ingredient>> mandatory,
                   ArrayList<ArrayList<Ingredient>> zeroOrOne, ArrayList<ArrayList<Ingredient>> oneOf,
                   ArrayList<ArrayList<Ingredient>> oneOrMore, ArrayList<ArrayList<Ingredient>> optional,
                   ArrayList<ArrayList<Ingredient>> any) {
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

    private static ArrayList<Cooker> selectCookers(RecipeTemplate recipeTemplate) {
       ArrayList<Cooker> cookers = new ArrayList<>();
        if (!recipeTemplate.cookersIsNone()) {
            recipeTemplate.getCookers().stream()
                    .filter(cooker -> !(cooker.getCooker().equals(Cooker._Cooker.ANY)))
                    .forEach(cooker -> Arrays.stream(Rarity.values())
                            .forEach(rarity1 -> {
                                if (rarity1 != Rarity.ANY)
                                    cookers.add(new Cooker(cooker.getCooker(), cooker.getDifficulty(), rarity1));
                            }));
            return cookers;
        } else
            return COOKERS_NONE;
    }

    private static ArrayList<Cooker> filterCookers(ArrayList<Cooker> cookers) {
        if (ExclusionFactory.hasCookerExclusions()) {
            return cookers.stream()
                    .filter(cooker -> ExclusionFactory.getCookerExclusions().stream()
                            .noneMatch( cookerExclusion -> {
                                if (cookerExclusion.isAnyCooker()) {
                                    return cookerExclusion.getCooker().equalsWithAnyCooker(cooker);
                                } else {
                                    return cookerExclusion.getCooker().equals(cooker);
                                }
                            }))
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
        return getIngredientSubstitutes(ingredient);
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

        ArrayList<RecipeTemplate> statesTemplates = selectRecipeFromResultItemTemplate(itemTemplates);
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

    private static  ArrayList<RecipeTemplate> selectRecipeFromResultItemTemplate(ArrayList<ItemTemplate> itemTemplates) {
        return RecipeTemplate.getRecipeTemplates().stream()
                .filter(recipeTemplate -> itemTemplates.stream()
                        .anyMatch(itemTemplate -> recipeTemplate.getResult().getItemTemplate().equals(itemTemplate)))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<RecipeTemplate> filterRecipeByState(ArrayList<RecipeTemplate> statesTemplates) {
        ArrayList<IngredientExclusion> ingredientExclusions = ExclusionFactory.getIngredientExclusions().stream()
                .filter(ingredientExclusion -> !ingredientExclusion.getIngredient().getCookedState().equals(CookedState.ANY) ||
                        !ingredientExclusion.getIngredient().getPreparedState().equals(PreparedState.ANY))
                .collect(Collectors.toCollection(ArrayList::new));
        return statesTemplates.stream()
                .filter(recipeTemplate -> ingredientExclusions.stream()
                        .noneMatch(ingredientExclusion -> ingredientExclusion.getIngredient().getCookedState().equals(
                                recipeTemplate.getResult().getCookedState()) ||
                                        ingredientExclusion.getIngredient().getPreparedState().equals(
                                                recipeTemplate.getResult().getPreparedState()
                                        )
                        ))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static ArrayList<Ingredient> makeIngredientVariantsFromMaterials(ArrayList<RecipeTemplate> recipeTemplates, Ingredient ingredient) {
        ArrayList<Material> materials = new ArrayList<>();
        if (ingredient.getItemTemplate().equals(ItemTemplate.ANY_MEAT)) {
            materials = Constants.MEAT_TYPES.stream()
                    .filter(material -> ExclusionFactory.getIngredientExclusions().stream()
                            .filter(ingredientExclusion -> !ingredientExclusion.getIngredient().getMaterial().equals(Material.ANY))
                            .noneMatch(ingredientExclusion -> ingredientExclusion.getIngredient().getMaterial().equals(material)))
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
                .forEach(recipeTemplate -> ingredients.add(new Ingredient(recipeTemplate.getResult(), recipeTemplate.getResult().getMaterial(),
                        LOSS_NONE, RATIO_NONE, 1)));

        return ingredients;
    }
}
