package com.endeavorms.velocity.qto.template.variable;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Search criteria for template variables.
 */
public class TemplateVariableSearchCriteria extends BaseSearchCriteria<TemplateVariable> {
    /** Lookup value company id, used to determine tenant. */
    @QueryParam("companyId")
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
