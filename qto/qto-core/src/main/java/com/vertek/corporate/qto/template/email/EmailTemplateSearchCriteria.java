package com.vertek.corporate.qto.template.email;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import javax.ws.rs.QueryParam;

/**
 * Search criteria for email template.
 */
public class EmailTemplateSearchCriteria extends BaseSearchCriteria<EmailTemplate> {
    /** A company ID, used to determine tenant when no sample entity is provided. */
    @QueryParam("companyId")
    private Long companyId;
    /** Entity ID, used with level to determine tenant. */
    @QueryParam("entityId")
    private Long entityId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }
}
