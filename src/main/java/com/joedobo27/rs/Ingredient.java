package com.joedobo27.rs;


import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.Cooker;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;

class Ingredient {

    private final ItemTemplate itemTemplate;
    /**
     * any cooked state.
     */
    private final CookedState cookedState;
    /**
     * any prepared state.
     */
    private final Cooker.PreparedState preparedState;
    /**
     * see Materials.convertMaterialStringIntoByte().
     */
    private final Material material;
    /**
     * Can be any item template.
     */
    private final ItemTemplate realTemplate;
    private final Integer difficulty;

    static final Ingredient INGREDIENT_NONE = new Ingredient(ItemTemplate.NONE, CookedState.RAW, Cooker.PreparedState.NONE, Material.NONE,
            ItemTemplate.NONE, 0);

    Ingredient(ItemTemplate itemTemplate, CookedState cookedState, Cooker.PreparedState preparedState, Material material, ItemTemplate realTemplate, int difficulty) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
    }
}
