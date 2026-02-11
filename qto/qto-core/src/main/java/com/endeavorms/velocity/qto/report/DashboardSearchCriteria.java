package com.endeavorms.velocity.qto.report;

import jakarta.ws.rs.QueryParam;
import java.util.List;

public class DashboardSearchCriteria {

    @QueryParam("tenantNames")
    private List<String> tenantNames;

    @QueryParam("masterCompanyNames")
    private List<String> masterCompanyNames;

    @QueryParam("companyNames")
    private List<String> companyNames;

    @QueryParam("serviceTypes")
    private List<String> serviceTypes;

    @QueryParam("providers")
    private List<String> providers;

    @QueryParam("serviceBilledTos")
    private List<String> serviceBilledTos;

    public List<String> getTenantNames() {
        return tenantNames;
    }

    public void setTenantNames(final List<String> tenantNames) {
        this.tenantNames = tenantNames;
    }

    public List<String> getMasterCompanyNames() {
        return masterCompanyNames;
    }

    public void setMasterCompanyNames(final List<String> masterCompanyNames) {
        this.masterCompanyNames = masterCompanyNames;
    }

    public List<String> getCompanyNames() {
        return companyNames;
    }

    public void setCompanyNames(final List<String> companyNames) {
        this.companyNames = companyNames;
    }

    public List<String> getServiceTypes() {
        return serviceTypes;
    }

    public void setServiceTypes(final List<String> serviceTypes) {
        this.serviceTypes = serviceTypes;
    }

    public List<String> getProviders() {
        return providers;
    }

    public void setProviders(final List<String> providers) {
        this.providers = providers;
    }

    public List<String> getServiceBilledTos() {
        return serviceBilledTos;
    }

    public void setServiceBilledTos(final List<String> serviceBilledTos) {
        this.serviceBilledTos = serviceBilledTos;
    }
}
