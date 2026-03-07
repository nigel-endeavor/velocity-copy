package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * From platform.
 * @author mmeehan
 * @since 11/29/12 5:35 PM
 */
@MappedSuperclass
public abstract class AbstractTenantOwnedEntity extends StandardVersionedBaseEntity implements TenantOwnedEntity<Long> {

    /** Tenant identifier.*/
    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @Override
    public Long getTenantId() {
        return tenantId;
    }
    @Override
    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

}