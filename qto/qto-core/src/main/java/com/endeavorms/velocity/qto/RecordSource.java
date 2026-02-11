package com.endeavorms.velocity.qto;

public enum RecordSource {

    INVENTORY_IMPORT("Inventory Import"),
    BULK_IMPORT("Bulk Import"),
    MANUAL_ENTRY("Manual Entry"),
    CONNECTBASE("ConnectBase"),
    PARSER("Parser"),
    WIP_IMPORT("WIP Import"),
    API("API"),
    UNKNOWN("Unknown"),
    CLONE("Clone"),
    MANUAL_MACD("Manual MACD");

    private String name;

    RecordSource(final String name) { this.name = name; }

    public String getName() { return name; }
}
