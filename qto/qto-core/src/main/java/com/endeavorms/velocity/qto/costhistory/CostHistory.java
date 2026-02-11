package com.endeavorms.velocity.qto.costhistory;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 11/1/2023
 */
@Entity
@Table(name = "cost_history")
public class CostHistory extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cost_history_id")
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "cost_type")
    private String costType;

    @Column(name = "type_provider")
    private String typeProvider;

    @Column(name = "old_value")
    private BigDecimal oldValue;

    @Column(name = "new_value")
    private BigDecimal newValue;

    @Column(name = "change_reason")
    private String changeReason;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "update_by")
    private String updateBy;

    @Override
    public Long getId() {
        return id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getTypeProvider() {
        return typeProvider;
    }

    public void setTypeProvider(final String typeProvider) {
        this.typeProvider = typeProvider;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(final String costType) {
        this.costType = costType;
    }

    public BigDecimal getOldValue() {
        return oldValue;
    }

    public void setOldValue(final BigDecimal oldValue) {
        this.oldValue = oldValue;
    }

    public BigDecimal getNewValue() {
        return newValue;
    }

    public void setNewValue(final BigDecimal newValue) {
        this.newValue = newValue;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(final String changeReason) {
        this.changeReason = changeReason;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(final Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(final String updateBy) {
        this.updateBy = updateBy;
    }
}
