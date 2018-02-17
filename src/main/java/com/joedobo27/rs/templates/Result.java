package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.*;

import javax.json.JsonObject;
import javax.json.JsonValue;

public class Result implements Constants {

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
    /**
     * Must references either the target item or an item in the mandatory group.
     */
    private final ItemTemplate referenceMaterial;
    private final ItemTemplate referenceRealTemplate;
    private final String description;
    private final Boolean useTemplateWeight;

    public Result(ItemTemplate itemTemplate, CookedState cookedState, PreparedState preparedState, Material material,
                  ItemTemplate realTemplate, int difficulty, ItemTemplate referenceMaterial, ItemTemplate referenceRealTemplate,
                  String description, Boolean useTemplateWeight) {
        this.itemTemplate = itemTemplate;
        this.cookedState = cookedState;
        this.preparedState = preparedState;
        this.material = material;
        this.realTemplate = realTemplate;
        this.difficulty = difficulty;
        this.referenceMaterial = referenceMaterial;
        this.referenceRealTemplate = referenceRealTemplate;
        this.description = description;
        this.useTemplateWeight = useTemplateWeight;
    }

    static Result buildFromJson(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        CookedState cookedState = CookedState.RAW;
        PreparedState preparedState = PreparedState.NONE;
        Material material = Material.ANY;
        ItemTemplate realTemplate = ItemTemplate.NONE;
        Integer difficulty = DIFFICULTY_NONE;
        ItemTemplate referenceMaterial = ItemTemplate.NONE;
        ItemTemplate referenceRealTemplate = ItemTemplate.NONE;
        String description = "";
        Boolean useTemplateWeight = false;
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
            if(jsonObject.get("difficulty").getValueType() == JsonValue.ValueType.NUMBER)
                difficulty = jsonObject.getInt("difficulty");
            else if (jsonObject.get("difficulty").getValueType() == JsonValue.ValueType.STRING){
                difficulty = Integer.valueOf(jsonObject.getString("difficulty"));
            }
        }
        if (jsonObject.containsKey("refmaterial")){
            referenceMaterial = ItemTemplate.getItemTemplateFromName(jsonObject.getString("refmaterial"));
        }
        if (jsonObject.containsKey("refrealtemplate")){
            referenceRealTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("refrealtemplate"));
        }
        if (jsonObject.containsKey("description")){
            description = jsonObject.getString("description");
        }
        if (jsonObject.containsKey("usetemplateweight")){
            useTemplateWeight = jsonObject.getBoolean("usetemplateweight");
        }
        return new Result(itemTemplate, cookedState, preparedState, material, realTemplate, difficulty, referenceMaterial,
                referenceRealTemplate, description, useTemplateWeight);
    }

    public ItemTemplate getReferenceMaterial() {
        return referenceMaterial;
    }

    public CookedState getCookedState() {
        return cookedState;
    }

    public PreparedState getPreparedState() {
        return preparedState;
    }

    public ItemTemplate getItemTemplate() {
        return itemTemplate;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemTemplate getRealTemplate() {
        return realTemplate;
    }

    public Integer getDifficulty() {
        return difficulty;
    }
}
