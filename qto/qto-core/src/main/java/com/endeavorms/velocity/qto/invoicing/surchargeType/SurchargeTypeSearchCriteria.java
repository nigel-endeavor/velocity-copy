package com.endeavorms.velocity.qto.invoicing.surchargeType;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Search criteria used for filtering service surcharge types.
 * @author fcurran
 * @since 7/14/2023
 */
public class SurchargeTypeSearchCriteria extends BaseSearchCriteria<SurchargeType> {
    @QueryParam("companyId")
    private Long companyId;
    /** Not a query param, but used to communicate tenant id from manager to dao. */
    private Long tenantId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }
}
