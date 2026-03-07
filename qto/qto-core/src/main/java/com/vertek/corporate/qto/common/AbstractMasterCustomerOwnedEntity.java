package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * @author rcasey
 * @since 10/12/2023
 */
@MappedSuperclass
public abstract class AbstractMasterCustomerOwnedEntity extends AbstractTenantOwnedEntity {

    /** Master Customer id. */
    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    public Long getMasterCustomerId() {
        return masterCustomerId;
    }

    public void setMasterCustomerId(final Long masterCustomerId) {
        this.masterCustomerId = masterCustomerId;
    }

}
