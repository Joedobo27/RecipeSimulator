package com.joedobo27.rs.items;

import java.util.Arrays;
import java.util.Objects;

/**
 * Recipes.convertCookingStateIntoByte()
 * Only one cooked state can exists for an item.
 * The first 16 values of auxData (of which 0-9 are used below) is reserved for remember anyone of these states.
 * see Item.setRightAuxData() and its uses.
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
    CHOCOLATE_COATED,
    ANY;


    public static CookedState getCookedStateFromName(String name) {
        return Arrays.stream(values())
                .filter(cookedState -> Objects.equals(name, cookedState.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

     public static CookedState getFromId(int i) {
        return Arrays.stream(values())
                .filter(cookedState -> cookedState.ordinal() == i)
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }
}
