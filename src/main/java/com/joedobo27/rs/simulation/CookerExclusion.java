package com.joedobo27.rs.simulation;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.Rarity;
import com.joedobo27.rs.templates.Cooker;

import javax.json.JsonArray;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class CookerExclusion implements Constants {

    private final Cooker cooker;

    private CookerExclusion(Cooker cooker) {
        this.cooker = cooker;
    }

    public static ArrayList<CookerExclusion> jsonArrayToCookerExclusion(JsonArray jsonArray) {
        ArrayList<CookerExclusion> toReturn = new ArrayList<>();

        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(jsonObject -> {
                    Cooker._Cooker cooker = Cooker._Cooker.ANY;
                    Integer difficulty = DIFFICULTY_NONE;
                    Rarity rarity = Rarity.ANY;
                    if (jsonObject.containsKey("item")){
                        cooker = Cooker._Cooker.getCookerFromName(jsonObject.getString("item"));
                    }
                    if (jsonObject.containsKey("difficulty")){
                        difficulty = jsonObject.getInt("difficulty");
                    }
                    if (jsonObject.containsKey("rarity")){
                        rarity = Rarity.getRarityFromName(jsonObject.getString("rarity"));
                    }
                    return new CookerExclusion(new Cooker(cooker, difficulty, rarity));
                })
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Cooker getCooker() {
        return cooker;
    }

    public boolean isAnyCooker() {
        return cooker.getCooker().equals(Cooker._Cooker.ANY);
    }

    public boolean isAnyRarity() { return cooker.getRarity().equals(Rarity.ANY); }

    public boolean equals(Cooker cooker) {
        return(this.isAnyCooker() || Objects.equals(this.cooker.getCooker(), cooker.getCooker())) &&
                (this.isAnyRarity() || Objects.equals(this.cooker.getRarity(), cooker.getRarity()));
    }
}
