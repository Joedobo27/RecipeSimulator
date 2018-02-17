package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Rarity;

import javax.annotation.Nonnull;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Container implements Constants, Comparable<Container> {

    private final _Container container;
    private final Integer difficulty;

    static ArrayList<Container> jsonArrayToContainers(JsonArray jsonArray) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(Container::jsonObjectToContainer)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static private Container jsonObjectToContainer(JsonObject jsonObject) {
        _Container container = _Container.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        if (jsonObject.containsKey("id")){
            container = _Container.getContainerFromName(jsonObject.getString("id"));
        }
        if (jsonObject.containsKey("difficulty")){
            difficulty = jsonObject.getInt("difficulty");
        }
        return new Container(container, difficulty);
    }

    public Container(_Container container, int difficulty) {
        this.container = container;
        this.difficulty = difficulty;
    }

    @Override
    public int compareTo(@Nonnull Container other) {
        return container.compareTo(other.container);
    }

    @Override public boolean equals(Object other) {
        if (!(other instanceof Container)) {
            return false;
        }
        Container otherContainer = (Container) other;
        return container == otherContainer.container;
    }

    @Override public int hashCode() {
        return container.ordinal() * 31 + difficulty * 17;
    }

    public enum _Container {
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

        _Container(ItemTemplate itemTemplate){
            this.itemTemplate = itemTemplate;
        }

        static _Container getContainerFromName(String name) {
            return Arrays.stream(_Container.values())
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
}
