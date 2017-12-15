package com.joedobo27.rs;

import com.joedobo27.rs.templates.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;

class Recipe {

    @Nonnull
    private final String name;
    private final int recipeId;
    private final boolean known;
    private final boolean nameable;
    @Nonnull
    private final Skill skill;
    @Nonnull
    private final ArrayList<CookerRecipe> cookers;
    @Nonnull
    private final ArrayList<ContainerRecipe> containers;
    @Nonnull
    private final Trigger trigger;
    @Nonnull
    private final Active active;
    @Nonnull
    private final TargetRecipe targetRecipe;
    @Nonnull
    private final ResultRecipe resultRecipe;
    @Nonnull
    private final ArrayList<IngredientGroupRecipe> ingredientGroupRecipes;
    @Nonnull
    private final MobDropRecipe mobDropRecipe;

    Recipe(@Nonnull String name, int recipeId, boolean known, boolean nameable, @Nonnull Skill skill,
           @Nonnull ArrayList<CookerRecipe> cookers, @Nonnull ArrayList<ContainerRecipe> containers,
           @Nonnull Trigger trigger, @Nonnull Active active, @Nonnull TargetRecipe targetRecipe,
           @Nonnull ResultRecipe resultRecipe, @Nonnull ArrayList<IngredientGroupRecipe> ingredientGroupRecipes,
           @Nonnull MobDropRecipe mobDropRecipe) {
        this.name = name;
        this.recipeId = recipeId;
        this.known = known;
        this.nameable = nameable;
        this.skill = skill;
        this.cookers = cookers;
        this.containers = containers;
        this.trigger = trigger;
        this.active = active;
        this.targetRecipe = targetRecipe;
        this.resultRecipe = resultRecipe;
        this.ingredientGroupRecipes = ingredientGroupRecipes;
        this.mobDropRecipe = mobDropRecipe;
    }
}
