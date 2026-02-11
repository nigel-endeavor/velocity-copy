package com.endeavorms.velocity.qto.location.inventoryview;

import java.math.BigDecimal;

public class InventoryWorklistMeta {

    private BigDecimal mrc;

    private BigDecimal mrr;

    private BigDecimal nrr;

    private BigDecimal annualRecurring;

    private BigDecimal openDisputeMrc;

    private BigDecimal openDisputeNrc;

    private Long macdCount;

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getMrr() {
        return mrr;
    }

    public void setMrr(final BigDecimal mrr) {
        this.mrr = mrr;
    }

    public BigDecimal getNrr() {
        return nrr;
    }

    public void setNrr(final BigDecimal nrr) {
        this.nrr = nrr;
    }

    public BigDecimal getAnnualRecurring() {
        return annualRecurring;
    }

    public void setAnnualRecurring(final BigDecimal annualRecurring) {
        this.annualRecurring = annualRecurring;
    }

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

    public Long getMacdCount() {
        return macdCount;
    }

    public void setMacdCount(final Long macdCount) {
        this.macdCount = macdCount;
    }
}
