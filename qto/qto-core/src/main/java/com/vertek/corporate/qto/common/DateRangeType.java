package com.vertek.corporate.qto.common;

public enum DateRangeType {
    BEFORE,
    AFTER,
    ON,
    ISEMPTY;

    private DateRangeType() {
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}
