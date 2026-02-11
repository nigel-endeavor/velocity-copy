package com.endeavorms.velocity.qto.invoicing.surchargeType;

import com.endeavorms.velocity.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * SurchargeType.
 */
@Entity
@Table(name = "surcharge_type")
public class SurchargeType extends AbstractTenantOwnedEntity {
    /** id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "surcharge_type_id")
    private Long id;
    /** surcharge_type. */
    @Column(name= "surcharge_type")
    private String type;
    /** surcharge_level. */
    @Column(name= "surcharge_level")
    private String level;
    /** surcharge_amount. */
    @Column(name= "surcharge_amount")
    private BigDecimal amount;
    /** start_date. */
    @Column(name= "start_date")
    private Date startDate;
    /** end_date. */
    @Column(name= "end_date")
    private Date endDate;

    @Override
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(final String level) {
        this.level = level;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }
}
