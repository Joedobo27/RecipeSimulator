package com.joedobo27.rs.items;

import java.util.Arrays;
import java.util.Objects;

/**
 * Multiple states can exists at once. A mask is used to toggle a bit position. some states use the same
 *      position and can't exists simultaneously.
 * see Recipes.convertPhysicalStateIntoByte(). Mutliple states are referenced with the "+".
 * This is stored in the last 4 bytes of auxData.
 */
public enum PreparedState {
    NONE(0B0),
    CHOPPED(0B00010000),
    DICED(0B00010000),
    GROUND(0B00010000),
    UNFERMENTED(0B00010000),
    ZOMBIEFIED(0B00010000),
    WHIPPED(0B00010000),
    MASHED(0B00100000),
    MINCED(0B00100000),
    FERMENTING(0B00100000),
    CLOTTED(0B00100000),
    WRAPPED(0B01000000),
    UNDISTILLED(0B01000000),
    SALTED(0B10000000),
    FRESH(0B10000000),
    ANY(0B0);

    private final int bitMask;

    PreparedState(int bitMask) {
            this.bitMask = bitMask;
    }

    public static PreparedState getPreparedStateFromName(String name) {
    return Arrays.stream(values())
            .filter(preparedState -> Objects.equals(name, preparedState.getName()))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }

    String getName() {
            return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }

    public int getBitMask() {
                return bitMask;
        }
}
