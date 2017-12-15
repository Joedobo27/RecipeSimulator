package com.joedobo27.rs;

public enum Trigger {
    NONE,
    TIME,
    HEAT,
    CREATE;

    String getName() {
        return this.name().toLowerCase().replace("_", " ");
    }
}
