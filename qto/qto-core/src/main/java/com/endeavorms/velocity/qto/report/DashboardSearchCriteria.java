package com.endeavorms.velocity.qto.report;

import java.util.ArrayList;
import java.util.List;

public class DashboardSearchCriteria {

    private List<String> tenantNames = new ArrayList<>();

    private List<String> masterCompanyNames = new ArrayList<>();

    private List<String> companyNames = new ArrayList<>();

    private List<String> serviceTypes = new ArrayList<>();

    private List<String> providers = new ArrayList<>();

    private List<String> serviceBilledTos = new ArrayList<>();

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
