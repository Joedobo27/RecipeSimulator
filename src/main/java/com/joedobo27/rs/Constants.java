package com.joedobo27.rs;

import com.joedobo27.rs.items.*;
import com.joedobo27.rs.templates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import static com.joedobo27.rs.items.ItemTemplateType.*;

public interface Constants {

    Integer LOSS_NONE = Integer.MIN_VALUE;
    Integer RATIO_NONE = Integer.MIN_VALUE;
    Integer AMOUNT_NONE = Integer.MIN_VALUE;
    Integer DIFFICULTY_NONE = Integer.MIN_VALUE;
    Integer RECIPE_ID_NONE = Integer.MIN_VALUE;
    String NAME_NONE = "";
    Active ACTIVE_NONE = new Active(ItemTemplate.NONE, CookedState.RAW, PreparedState.NONE,
            Material.ANY, ItemTemplate.NONE, DIFFICULTY_NONE, LOSS_NONE, RATIO_NONE);
    Container CONTAINER_NONE = new Container(Container._Container.NONE, DIFFICULTY_NONE);
    ArrayList<Container> CONTAINERS_NONE = new ArrayList<>(Collections.singleton(CONTAINER_NONE));
    Cooker COOKER_NONE = new Cooker(Cooker._Cooker.ANY, DIFFICULTY_NONE, Rarity.COMMON);
    ArrayList<Cooker> COOKERS_NONE = new ArrayList<>(Collections.singleton(COOKER_NONE));
    Ingredient INGREDIENT_NONE = new Ingredient(ItemTemplate.NONE, CookedState.RAW, PreparedState.NONE, Material.ANY,
            ItemTemplate.NONE,  DIFFICULTY_NONE, LOSS_NONE, RATIO_NONE, AMOUNT_NONE);
    IngredientGroup INGREDIENT_GROUP_NONE = new IngredientGroup(IngredientGroup._IngredientGroup.NONE,
            new ArrayList<>(Collections.singletonList(Ingredient.INGREDIENT_NONE)));
    MobDrop MOB_DROP_NONE = new MobDrop(Creature.NONE, Rarity.COMMON);
    Result RESULT_NONE = new Result(ItemTemplate.NONE, CookedState.RAW, PreparedState.NONE,
            Material.ANY, ItemTemplate.NONE, DIFFICULTY_NONE, ItemTemplate.NONE, ItemTemplate.NONE,"",
            false);
    Target TARGET_NONE = new Target(ItemTemplate.NONE, CookedState.RAW, PreparedState.NONE,
            Material.ANY, ItemTemplate.NONE, DIFFICULTY_NONE, LOSS_NONE, RATIO_NONE, Creature.NONE);
    ArrayList<ItemTemplateType> ANY_TEMPLATE_TYPES = new ArrayList<>(Arrays.asList(ANY_CEREAL_FOOD_GROUP, ANY_VEGGIE_FOOD_GROUP, ANY_CHEESE_FOOD_GROUP,
            ANY_MILK_FOOD_GROUP, ANY_MEAT_FOOD_GROUP, ANY_FISH_FOOD_GROUP, ANY_MUSHROOM_FOOD_GROUP, ANY_HERB_FOOD_GROUP,
            ANY_FLOWER_FOOD_GROUP, ANY_BERRY_FOOD_GROUP, ANY_NUT_FOOD_GROUP, ANY_FRUIT_FOOD_GROUP, ANY_SPICE_FOOD_GROUP));
    ArrayList<Material> MEAT_TYPES = new ArrayList<>(Arrays.asList(Material.BEAR, Material.BEEF, Material.CANINE, Material.DRAGON,
            Material.FELINE, Material.FOWL, Material.GAME, Material.HORSE, Material.HUMAN, Material.HUMANOID, Material.INSECT,
            Material.LAMB, Material.PORK, Material.SEAFOOD, Material.SNAKE, Material.TOUGH));
}
