package com.joedobo27.rs.templates;

import com.joedobo27.rs.*;
import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;
import com.joedobo27.rs.items.PreparedState;

import javax.json.JsonObject;

public class Target implements Constants {

    private final ItemTemplate itemTemplate;
    /**
     * any cooked state.
     */
    private final CookedState cookedState;
    /**
     * any prepared state.
     */
    private final PreparedState preparedState;
    /**
     * see Materials.convertMaterialStringIntoByte().
     */
    private final Material material;
    /**
     * Can be any item template.
     */
    private final ItemTemplate realTemplate;
    private final Integer difficulty;
    private final Integer loss;
    private final Integer ratio;
    private final Creature creature;

    public Target(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                  ItemTemplate realTemplate, int difficulty, int loss, int ratio, Creature creature) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.loss = loss;
        this.ratio = ratio;
        this.creature = creature;
    }

    static Target buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.ANY;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;
        Creature creature = Creature.NONE;
        if (jsonObject.containsKey("id"))
            itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("id"));
        if (jsonObject.containsKey("cstate")){
            cookedState = CookedState.getCookedStateFromName(jsonObject.getString("cstate"));
        }
        if (jsonObject.containsKey("pstate")){
            preparedState = PreparedState.getPreparedStateFromName(jsonObject.getString("pstate"));
        }
        if (jsonObject.containsKey("material")){
            material = Material.getMaterialFromName(jsonObject.getString("material"));
        }
        if (jsonObject.containsKey("realtemplate")){
            realTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("realtemplate"));
        }
        if (jsonObject.containsKey("difficulty")){
            difficulty = jsonObject.getInt("difficulty");
        }
        if (jsonObject.containsKey("loss")){
            loss = jsonObject.getInt("loss");
        }
        if (jsonObject.containsKey("ratio")){
            ratio = jsonObject.getInt("ratio");
        }

        return new Target(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio,
                creature);
    }
}
