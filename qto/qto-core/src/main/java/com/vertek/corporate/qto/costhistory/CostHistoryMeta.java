package com.vertek.corporate.qto.costhistory;

import java.math.BigDecimal;

/**
 * Container for cost history grid metadata.
 */
public class CostHistoryMeta {

    /** Cost change in the last 365 days. */
    private BigDecimal costChangeThisYear;

    /** Cost change since the service was created. */
    private BigDecimal costChangeLifetime;

    public BigDecimal getCostChangeThisYear() {
        return costChangeThisYear;
    }

    public void setCostChangeThisYear(final BigDecimal costChangeThisYear) {
        this.costChangeThisYear = costChangeThisYear;
    }

    public BigDecimal getCostChangeLifetime() {
        return costChangeLifetime;
    }

    public void setCostChangeLifetime(final BigDecimal costChangeLifetime) {
        this.costChangeLifetime = costChangeLifetime;
    }
}
