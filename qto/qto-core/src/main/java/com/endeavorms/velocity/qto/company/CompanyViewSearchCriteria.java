package com.endeavorms.velocity.qto.company;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import java.util.List;

/**
 * @author rcasey
 * @since 1/2/2024
 */
public class CompanyViewSearchCriteria extends BaseSearchCriteria<CompanyView> {

    private String search;

    private String type;

    private String tenantName;

    private Long masterCustomerId;

    /** Active flag.*/
    private Boolean active;

    private Boolean onboarding;

    private List<String> assignedTo;

    private String clientId;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(final String tenantName) {
        this.tenantName = tenantName;
    }

    public Long getMasterCustomerId() {
        return masterCustomerId;
    }

    public void setMasterCustomerId(final Long masterCustomerId) {
        this.masterCustomerId = masterCustomerId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Boolean getOnboarding() {
        return onboarding;
    }

    public void setOnboarding(final Boolean onboarding) {
        this.onboarding = onboarding;
    }

    public List<String> getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final List<String> assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }
}
