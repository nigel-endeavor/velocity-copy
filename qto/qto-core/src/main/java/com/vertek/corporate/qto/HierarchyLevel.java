package com.vertek.corporate.qto;

/**
 * Enum for the hierarchy level.
 */
public enum HierarchyLevel {
    SERVICE("service"),
    LOCATION("location"),
    ORDER("order");

    /** Name of the hierarchy level. */
    private String name;

    /**
     * Constructor for initializing the enum.
     * @param name the name of the hierarchy level.
     */
    HierarchyLevel(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
