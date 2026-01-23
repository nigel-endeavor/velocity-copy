package com.vertek.corporate.qto.dispute;

import java.math.BigDecimal;

/**
 * Container for dispute grid meta data.
 */
public class DisputeGridMeta {
    /**
     * Total open disputed MRC for the currently displayed result set.
     */
    private BigDecimal openDisputeMrc;

    /**
     * Total open disputed NRC for the currently displayed result set.
     */
    private BigDecimal openDisputeNrc;

    public BigDecimal getOpenDisputeMrc() {
        return openDisputeMrc;
    }

    public void setOpenDisputeMrc(final BigDecimal openDisputeMrc) {
        this.openDisputeMrc = openDisputeMrc;
    }

    public BigDecimal getOpenDisputeNrc() {
        return openDisputeNrc;
    }

    public void setOpenDisputeNrc(final BigDecimal openDisputeNrc) {
        this.openDisputeNrc = openDisputeNrc;
    }
}
