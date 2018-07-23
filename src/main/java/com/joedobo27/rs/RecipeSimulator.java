package com.joedobo27.rs;


import com.joedobo27.rs.simulation.ExclusionFactory;
import com.joedobo27.rs.simulation.RecipeFactory;
import com.joedobo27.rs.templates.RecipeTemplate;

import java.io.InputStream;
import java.util.ArrayList;

public class RecipeSimulator {

    public static void main(String[] args) {
        System.out.println("Hello, World!");
        RecipeTemplate.importAllRecipe("C:/Users/Jason/Documents/WU/WU-Server/dist/recipes/");
        //InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("./recipe 1355.json");
        //RecipeTemplate.importRecipe(inputStream);

        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("exclusions.json");
        ExclusionFactory.importExclusions(inputStream);

        ArrayList<RecipeTemplate> recipeTemplates = RecipeTemplate.getImportedRecipeTemplates();
        RecipeTemplate template = RecipeTemplate.getImportedRecipeTemplates().stream()
                .filter(recipeTemplate -> recipeTemplate.getRecipeId() == Recipe.CHOPPED_VEG.getId())
                .findFirst()
                .orElseThrow(RuntimeException::new);
        RecipeFactory.buildRecipe(template);
        int i = 1;
    }

}
