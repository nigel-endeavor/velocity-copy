package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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
