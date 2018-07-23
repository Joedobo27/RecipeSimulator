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

public class Cooker implements Constants, Comparable<Cooker> {

    private final _Cooker cooker;
    private final Integer difficulty;
    private final Rarity rarity;

    static ArrayList<Cooker> jsonArrayToCookers(JsonArray jsonArray) {
        ArrayList<Cooker> cookers = IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(Cooker::jsonObjectToContainer)
                .filter(cooker1 -> !Objects.equals(cooker1, COOKER_NONE))
                .collect(Collectors.toCollection(ArrayList::new));
        if (cookers.isEmpty())
            return COOKERS_NONE;
        else
            return cookers;
    }

    private static Cooker jsonObjectToContainer(JsonObject jsonObject) {
        _Cooker cooker = COOKER_NONE.cooker;
        Integer difficulty = COOKER_NONE.difficulty;
        Rarity rarity = COOKER_NONE.rarity;
        if (jsonObject.containsKey("id")) {
            cooker = _Cooker.getCookerFromName(jsonObject.getString("id"));
        }
        if (jsonObject.containsKey("difficulty")) {
            difficulty = jsonObject.getInt("difficulty");
        }
        if (jsonObject.containsKey("rarity")) {
            rarity = Rarity.getRarityFromName(jsonObject.getString("rarity"));
        }
        if (Objects.equals(cooker, COOKER_NONE.cooker))
            return COOKER_NONE;
        return new Cooker(cooker, difficulty, rarity);
    }

    public Cooker(_Cooker cooker, Integer difficulty, Rarity rarity) {
        this.cooker = cooker;
        this.difficulty = difficulty;
        this.rarity = rarity;
    }

    public Cooker(Cooker cooker, Rarity rarity) {
        this.cooker = cooker.cooker;
        this.difficulty = cooker.difficulty;
        this.rarity = rarity;
    }


    public _Cooker getCooker() {
        return cooker;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public Rarity getRarity() { return rarity; }

    @Override
    public int compareTo(@Nonnull Cooker other) {
        return cooker != other.cooker ? cooker.compareTo(other.cooker)
                : rarity.compareTo(other.rarity);
    }

    public boolean equalsWithAnyCooker(Object other) {
        if (!(other instanceof Cooker)) {
            return false;
        }
        Cooker otherCooker = (Cooker) other;
        return rarity.equals(otherCooker.rarity);
    }

    @Override public boolean equals(Object other) {
        if (!(other instanceof Cooker)) {
            return false;
        }
        Cooker otherCooker = (Cooker) other;
        return cooker.equals(otherCooker.cooker) && rarity.equals(otherCooker.rarity);
    }

    @Override public int hashCode() {
        return cooker.ordinal() * 31 + difficulty * 17 + rarity.ordinal() * 7;
    }

    public enum _Cooker {
        NONE(ItemTemplate.NONE),
        ANY(null),
        FORGE(ItemTemplate.FORGE),
        OVEN(ItemTemplate.OVEN),
        CAMPFIRE(ItemTemplate.CAMPFIRE),
        STILL(ItemTemplate.STILL);

        private final ItemTemplate itemTemplate;
        public static final String cookerGroupJsonLabel = "cookers";

        _Cooker(ItemTemplate itemTemplate) {
            this.itemTemplate = itemTemplate;
        }

        public static _Cooker getCookerFromName(String name) {
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
}
