package com.endeavorms.velocity.qto.template.variable;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


/**
 * Search criteria for template variables.
 */
public class TemplateVariableSearchCriteria extends BaseSearchCriteria<TemplateVariable> {
    /** Lookup value company id, used to determine tenant. */
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }
}
