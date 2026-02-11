package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

/**
 * From platform.
 * @author mmeehan
 * @since 1/7/13 3:40 PM
 */
@Entity
@Table(name = "company", schema = "platform")
@JsonIgnoreProperties("tenant")
@Inheritance(strategy = InheritanceType.JOINED)
public class TenantOwnedCompany extends AbstractCompany {

    /** Tenant identifier.*/
//    @JsonView(JsonViews.Internal.class)
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
