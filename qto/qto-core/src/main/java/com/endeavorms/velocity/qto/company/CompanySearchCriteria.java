package com.endeavorms.velocity.qto.company;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey
 * @since 1/19/2023
 */
public class CompanySearchCriteria extends BaseSearchCriteria<Company> {
    private String type;

    private String search;

    private String name;

    private List<String> tenants = new ArrayList<>();

    private List<String> masterCustomers = new ArrayList<>();

    /** Active flag.*/
    private Boolean active;

    private String clientId;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<String> getTenants() {
        return tenants;
    }

    public void setTenants(final List<String> tenants) {
        this.tenants = tenants;
    }

    public List<String> getMasterCustomers() {
        return masterCustomers;
    }

    public void setMasterCustomers(final List<String> masterCustomers) {
        this.masterCustomers = masterCustomers;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }
}
