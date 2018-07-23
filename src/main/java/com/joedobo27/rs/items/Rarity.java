package com.joedobo27.rs.items;


import java.util.Arrays;
import java.util.Objects;

public enum Rarity {
    COMMON,
    RARE,
    SUPREME,
    FANTASTIC,
    ANY,
    NONE;

    public static Rarity getRarityFromName(String name) {
        return Arrays.stream(values())
                .filter(rarity -> Objects.equals(name, rarity.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }
}
