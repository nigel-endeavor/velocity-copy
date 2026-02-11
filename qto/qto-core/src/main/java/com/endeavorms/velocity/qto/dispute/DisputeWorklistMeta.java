package com.endeavorms.velocity.qto.dispute;

import java.math.BigDecimal;

/**
 * Container for dispute worklist meta data.
 */
public class DisputeWorklistMeta {
    /**
     * Total dispute MRC for the currently displayed result set.
     */
    private BigDecimal totalDisputeMrc;

    /**
     * Total dispute NRC for the currently displayed result set.
     */
    private BigDecimal totalDisputeNrc;

    public BigDecimal getTotalDisputeMrc() {
        return totalDisputeMrc;
    }

    public void setTotalDisputeMrc(final BigDecimal totalDisputeMrc) {
        this.totalDisputeMrc = totalDisputeMrc;
    }

    public BigDecimal getTotalDisputeNrc() {
        return totalDisputeNrc;
    }

    public void setTotalDisputeNrc(final BigDecimal totalDisputeNrc) {
        this.totalDisputeNrc = totalDisputeNrc;
    }
}
