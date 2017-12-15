package com.joedobo27.rs.items;

import java.util.Arrays;
import java.util.Objects;

public enum Cooker {
    NONE(ItemTemplate.NONE),
    FORGE(ItemTemplate.FORGE),
    OVEN(ItemTemplate.OVEN),
    CAMPFIRE(ItemTemplate.CAMPFIRE),
    STILL(ItemTemplate.STILL);

    private final ItemTemplate itemTemplate;
    public static final String cookerGroupJsonLabel = "cookers";

    Cooker(ItemTemplate itemTemplate) {
        this.itemTemplate = itemTemplate;
    }

    static Cooker getCookerFromName(String name) {
        return Arrays.stream(values())
                .filter(cooker -> Objects.equals(name, cooker.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }

    boolean hasValidJsonLabels(String label) {
        return Objects.equals(label, "id") || Objects.equals(label, "difficulty");
    }

}
