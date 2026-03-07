package com.vertek.corporate.qto.invoicing.levelOfEffort;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * @author rcasey
 * @since 7/12/2023
 */
@Entity
@Table(name = "level_of_effort")
public class LevelOfEffort extends AbstractTenantOwnedEntity implements Comparable<LevelOfEffort> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_of_effort_id")
    private Long id;

    @Column(name = "level_of_effort")
    private String levelOfEffort;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "sort_order")
    private Long sortOrder;

    @Override
    public Long getId() {
        return id;
    }

    public String getLevelOfEffort() {
        return levelOfEffort;
    }

    public void setLevelOfEffort(final String levelOfEffort) {
        this.levelOfEffort = levelOfEffort;
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

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(final Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int compareTo(LevelOfEffort other) {
        return this.sortOrder.compareTo(other.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLevelOfEffort());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LevelOfEffort other = (LevelOfEffort) obj;
        return Objects.equals(getLevelOfEffort(), other.getLevelOfEffort());
    }
}
