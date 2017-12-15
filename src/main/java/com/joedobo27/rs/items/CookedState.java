package com.joedobo27.rs.items;

/**
 * Recipes.convertCookingStateIntoByte()
 * Only one cooked state can exists for an item.
 */
public enum CookedState {
    RAW,
    FRIED,
    GRILLED,
    BOILED,
    ROASTED,
    STEAMED,
    BAKED,
    COOKED,
    CANDIED,
    CHOCOLATE_COATED;


    int getId(){
        return this.ordinal();
    }

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }
}
