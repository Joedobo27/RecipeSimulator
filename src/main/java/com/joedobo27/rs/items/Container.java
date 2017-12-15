package com.joedobo27.rs.items;


import java.util.Arrays;
import java.util.Objects;

public enum Container {
    NONE(ItemTemplate.NONE),
    FRYING_PAN(ItemTemplate.FRYING_PAN),
    POTTERY_JAR(ItemTemplate.POTTERY_JAR),
    POTTERY_BOWL(ItemTemplate.POTTERY_BOWL),
    CORPSE(ItemTemplate.CORPSE),
    OPEN_HELM(ItemTemplate.OPEN_HELM),
    SAUCE_PAN(ItemTemplate.SAUCE_PAN),
    CAULDRON(ItemTemplate.CAULDRON),
    WINE_BARREL(ItemTemplate.WINE_BARREL),
    PIE_DISH(ItemTemplate.PIE_DISH),
    CAKE_TIN(ItemTemplate.CAKE_TIN),
    BAKING_STONE(ItemTemplate.BAKING_STONE),
    ROASTING_DISH(ItemTemplate.ROASTING_DISH),
    PLATE(ItemTemplate.PLATE),
    MUSHROOM(ItemTemplate.MUSHROOM),
    SAUSAGE_SKIN(ItemTemplate.SAUSAGE_SKIN),
    BOILER(ItemTemplate.BOILER);


    private final ItemTemplate itemTemplate;
    public static final String ContainerGroupJsonLabel = "containers";

    Container(ItemTemplate itemTemplate){
        this.itemTemplate = itemTemplate;
    }

    static Container getContainerFromName(String name) {
        return Arrays.stream(values())
                .filter(container -> Objects.equals(name, container.getName()))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    String getName() {
        return this.name().toLowerCase().replace("_", " ").replace("0", "-");
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }
}
