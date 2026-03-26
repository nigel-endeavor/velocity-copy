package com.endeavorms.velocity.qto.invoicing.surcharge;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeType;
import org.hibernate.annotations.Formula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * Surcharge.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "surcharge")
public class Surcharge extends AbstractMasterCustomerOwnedEntity {
    /** id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surcharge_id")
    private Long id;
    /** Surcharge Type. */
    @ManyToOne
    @JoinColumn(name = "surcharge_type_id")
    private SurchargeType surchargeType;
    /** surcharge_date. */
    @Column(name = "surcharge_date")
    private Date surchargeDate;
    /** added_by. */
    @Column(name = "added_by")
    private String addedBy;
    /** invoice_charge_id. */
    @Column(name = "invoice_charge_id")
    private Long invoiceChargeId;
    /** Calculated flag indicating whether the surcharge is part of a finalized invoice. */
    @Formula("(select case when i.invoice_status = 'Final' then true else false end" +
            " from surcharge s" +
            "          left join invoice_charge ic on ic.invoice_charge_id = s.invoice_charge_id" +
            "          left join invoice i on i.invoice_id = ic.invoice_id" +
            " where s.surcharge_id = surcharge_id)")
    private boolean finalized;

    @Override
    public Long getId() {
        return id;
    }

    @PrePersist
    @PreUpdate
    void preUpdate() {
        setAddedBy(SecurityUtils.getLoggedInUser());
    }

    public SurchargeType getSurchargeType() {
        return surchargeType;
    }

    public void setSurchargeType(final SurchargeType surchargeType) {
        this.surchargeType = surchargeType;
    }

    public Date getSurchargeDate() {
        return surchargeDate;
    }

    public void setSurchargeDate(final Date surchargeDate) {
        this.surchargeDate = surchargeDate;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(final String addedBy) {
        this.addedBy = addedBy;
    }

    public Long getInvoiceChargeId() {
        return invoiceChargeId;
    }

    public void setInvoiceChargeId(final Long invoiceChargeId) {
        this.invoiceChargeId = invoiceChargeId;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public void setFinalized(final boolean finalized) {
        this.finalized = finalized;
    }
}
