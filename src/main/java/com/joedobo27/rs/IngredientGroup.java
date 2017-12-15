package com.joedobo27.rs;

@SuppressWarnings("unused")
enum IngredientGroup {
    NONE,
    MANDATORY,
    OPTIONAL,
    ONEOF,
    ZEROORONE,
    ONEORMORE,
    ANY;

    public static final String ingredientGroupJsonLabel ="ingredients group";

    String getName() {
        return this.name().toLowerCase().replace("_", " ");
    }

    /**
     * @return true group can have multiple groups, each stored in its own list.
     */
    boolean canHaveListSubGroup(){
        return this == ONEOF || this == ZEROORONE || this == ONEORMORE;
    }
}
