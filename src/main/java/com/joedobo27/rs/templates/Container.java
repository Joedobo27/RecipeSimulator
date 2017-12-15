package com.joedobo27.rs.templates;

import com.joedobo27.rs.Constants;
import com.joedobo27.rs.items.ItemTemplate;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Container implements Constants {

    private final ItemTemplate itemTemplate;
    private final Integer difficulty;
    final static Container CONTAINER_NONE = new Container(ItemTemplate.NONE, DIFFICULTY_NONE);

    static ArrayList<Container> getContainersFromString(JsonArray jsonArray) {
        return IntStream.range(0, jsonArray.size())
                .mapToObj(jsonArray::getJsonObject)
                .map(Container::jsonObjectToContainer)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    static private Container jsonObjectToContainer(JsonObject jsonObject) {
        ItemTemplate itemTemplate = ItemTemplate.NONE;
        Integer difficulty = 0;
        if (jsonObject.containsKey("id")){
            itemTemplate = ItemTemplate.getItemTemplateFromName(jsonObject.getString("id"));
        }
        if (jsonObject.containsKey("difficulty")){
            difficulty = jsonObject.getInt("difficulty");
        }
        return new Container(itemTemplate, difficulty);
    }

    private Container(ItemTemplate itemTemplate, int difficulty) {
        this.itemTemplate = itemTemplate;
        this.difficulty = difficulty;
    }
}
