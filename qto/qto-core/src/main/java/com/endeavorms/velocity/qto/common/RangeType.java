package com.endeavorms.velocity.qto.common;

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
