package com.vertek.corporate.qto.common;

public enum RangeType {
    ISEMPTY,
    GT,
    LT,
    EQ;

    private RangeType() {
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}
