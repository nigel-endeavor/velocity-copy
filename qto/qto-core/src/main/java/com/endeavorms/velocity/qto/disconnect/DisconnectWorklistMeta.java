package com.endeavorms.velocity.qto.disconnect;

import java.math.BigDecimal;

/**
 * Container for disconnect worklist meta data.
 */
public class DisconnectWorklistMeta {
    /**
     * Total MRCs for the currently displayed result set.
     */
    private BigDecimal totalMrc;

    private BigDecimal totalMrr;

    /**
     * Total early termination fees for the currently displayed result set.
     */
    private BigDecimal totalEarlyTerminationFee;

    public BigDecimal getTotalMrc() {
        return totalMrc;
    }

    public void setTotalMrc(final BigDecimal totalMrc) {
        this.totalMrc = totalMrc;
    }

    public BigDecimal getTotalMrr() {
        return totalMrr;
    }

    public void setTotalMrr(final BigDecimal totalMrr) {
        this.totalMrr = totalMrr;
    }

    public BigDecimal getTotalEarlyTerminationFee() {
        return totalEarlyTerminationFee;
    }

    public void setTotalEarlyTerminationFee(final BigDecimal totalEarlyTerminationFee) {
        this.totalEarlyTerminationFee = totalEarlyTerminationFee;
    }
}
