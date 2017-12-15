package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.CookedState;
import com.joedobo27.rs.items.ItemTemplate;
import com.joedobo27.rs.items.Material;
import com.joedobo27.rs.items.PreparedState;

import javax.json.JsonObject;

class Active implements Constants {

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

    static final Active ACTIVE_NONE = new Active(ItemTemplate.NONE, CookedState.RAW, PreparedState.NONE,
            Material.NONE, ItemTemplate.NONE, 0, 0, 0);

    private Active(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                   ItemTemplate realTemplate, Integer difficulty, Integer loss, Integer ratio) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.loss = loss;
        this.ratio = ratio;
    }

    static Active buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.NONE;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        Integer loss = LOSS_NONE;
        Integer ratio = RATIO_NONE;

        if (jsonObject.containsKey("id"))
            itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("id"));
        if (jsonObject.containsKey("cstate")){
            int i = 1;
        }
        if (jsonObject.containsKey("pstate")){
            int i = 1;
        }
        if (jsonObject.containsKey("material")){
            int i = 1;
        }
        if (jsonObject.containsKey("realtemplate")){
            int i = 1;
        }
        if (jsonObject.containsKey("difficulty")){
            int i = 1;
        }
        if (jsonObject.containsKey("loss")){
            int i = 1;
        }
        if (jsonObject.containsKey("ratio")){
            int i = 1;
        }
        return new Active(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, loss, ratio);
    }

}
