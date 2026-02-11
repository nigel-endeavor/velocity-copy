package com.endeavorms.velocity.qto.report;

public enum ReportList {
    INVENTORY_REPORT("inventory"),
    WIP_REPORT("wip");

    private String name;

    ReportList(final String name) { this.name = name; }

    public String getName() { return name; }
}
