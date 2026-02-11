package com.endeavorms.velocity.qto.common;

public enum StringRangeType {
    CONTAINS("Contains"),
    STARTS_WITH("Starts with"),
    ENDS_WITH("Ends with"),
    EQUALS("Equals");

    protected String displayName;

    private StringRangeType(String var3) {
        this.displayName = var3;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}
